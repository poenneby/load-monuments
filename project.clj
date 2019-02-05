(defproject load-monuments "0.1.0-SNAPSHOT"
  :description "A script that loads monuments into Datomic"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                [cheshire "5.8.1"]
                [com.datomic/datomic-free "0.9.5697"]]
  :main ^:skip-aot load-monuments.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
