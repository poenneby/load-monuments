(ns load-monuments.core
  (:require [datomic.api :as d]
            [clojure.java.io :as io]
            [cheshire.core :refer [parse-string]]))

(defonce monuments (parse-string (slurp (io/resource "merimee-MH.json")) true))

(def db-uri "datomic:free://localhost:4334/monumental")

(defn create-db []
  (d/create-database db-uri))

(defn connect []
  (println "Creating database")
  (d/connect db-uri))

(defn create-schema [conn]
  (println "Creating schema")
  (let [monument-schema [{:db/ident :monument/ref
                          :db/valueType :db.type/string
                          :db/cardinality :db.cardinality/one
                          :db/doc "The REF of the monument"}
                         {:db/ident :monument/tico
                          :db/valueType :db.type/string
                          :db/cardinality :db.cardinality/one
                          :db/doc "The TICO of the monument"}
                         {:db/ident :monument/insee
                          :db/valueType :db.type/string
                          :db/cardinality :db.cardinality/one
                          :db/doc "The INSEE of the monument"}
                         {:db/ident :monument/ppro
                          :db/valueType :db.type/string
                          :db/cardinality :db.cardinality/one
                          :db/doc "The PPRO of the monument"}
                         {:db/ident :monument/dpt
                          :db/valueType :db.type/string
                          :db/cardinality :db.cardinality/one
                          :db/doc "The DPT of the monument"}
                         {:db/ident :monument/reg
                          :db/valueType :db.type/string
                          :db/cardinality :db.cardinality/one
                          :db/fulltext true
                          :db/doc "The region of the monument"}]]
    (d/transact conn monument-schema)))

(defn load-data [conn]
  (println "Loading data...")
  (let [first-monuments (vec (for [monument (take 100000 monuments)
                                        :let [entity {:monument/ref (:REF monument)
                                                      :monument/tico (:TICO monument)
                                                      :monument/insee (:INSEE monument)
                                                      :monument/ppro (:PPRO monument)
                                                      :monument/dpt (:DPT monument)
                                                      :monument/reg (:REG monument)}]
                                        :when (not (empty? monument))] entity))]
      (d/transact conn first-monuments)
      (println (str "Loaded " (count first-monuments) " monument(s)"))))

(defn -main
  "Load the monuments into our datomic database"
  [& args]
  (create-db)
  (let [conn (connect)]
    (create-schema conn)
    (load-data conn))
  (System/exit 0))






