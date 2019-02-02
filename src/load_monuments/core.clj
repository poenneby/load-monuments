(ns load-monuments.core
  (:require [datomic.client.api :as d]
            [clojure.java.io :as io]
            [cheshire.core :refer [parse-string]]))

(defonce monuments (parse-string (slurp (io/resource "merimee-MH.json")) true))

(defn connect []
  (let [cfg {:server-type :peer-server
          :access-key "myaccesskey"
          :secret "mysecret"
          :endpoint "localhost:8998"}
         client (d/client cfg)]
     (d/connect client {:db-name "monumental"})))


(defn create-schema [conn]
  (let [monument-schema [{:db/ident :monument/ref
                          :db/valueType :db.type/string
                          :db/cardinality :db.cardinality/one
                          :db/doc "The REF of the monument"}
                         {:db/ident :monument/tico
                          :db/valueType :db.type/string
                          :db/cardinality :db.cardinality/one
                          :db/doc "The TICO of the monument"}
                         {:db/ident :monument/reg
                          :db/valueType :db.type/string
                          :db/cardinality :db.cardinality/one
                          :db/fulltext true
                          :db/doc "The region of the monument"}]]
    (d/transact conn {:tx-data monument-schema})))

(defn -main
  "Load the monuments into our datomic database"
  [& args]
  (let [conn (connect)]
    (create-schema conn)
     (let [first-monuments (vec (for [monument (take 10000 monuments)] {:monument/ref (:REF monument)
                                                                        :monument/tico (:TICO monument)
                                                                        :monument/reg (:REG monument)
                                                                        }))]
       (d/transact conn {:tx-data first-monuments}))

    (def db (d/db conn))
      (println (str "We have a monuments " monuments))))






