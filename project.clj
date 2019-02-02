(defproject load-monuments "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [cheshire "5.8.1"]
                 [com.datomic/client-pro "0.8.28"]]
  :main ^:skip-aot load-monuments.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
