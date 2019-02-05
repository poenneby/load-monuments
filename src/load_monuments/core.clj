(ns load-monuments.core
  (:require [datomic.api :as d]
            [clojure.java.io :as io]
            [cheshire.core :refer [parse-string]]))

(defonce monuments (parse-string (slurp (io/resource "merimee-MH.json")) true))

(defn connect []
  (let [db-uri "datomic:free://localhost:4334/monumental"]
     (d/connect db-uri)))

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
    (d/transact conn monument-schema)))

(defn -main
  "Load the monuments into our datomic database"
  [& args]
  (let [conn (connect)]
    (create-schema conn)
    (let [first-monuments (vec (for [monument (take 100000 monuments)
                                        :let [entity {:monument/ref (:REF monument)
                                                      :monument/tico (:TICO monument)
                                                      :monument/reg (:REG monument)}]
                                        :when (not (empty? monument))] entity))]
      (d/transact conn first-monuments)
      (println (str "Loaded " (count first-monuments) " monument(s)"))))
  (System/exit 0))






