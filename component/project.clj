(defproject component "0.1.0-SNAPSHOT"
  :description "Server using Stuart Sierra's 'Component' library"
  :url "https://github.com/jconti/servers"

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/spec.alpha "0.2.176"]
                 [ring "1.7.1"]
                 [seancorfield/next.jdbc "1.0.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [com.microsoft/sqljdbc4 "4.2"]
                 [com.stuartsierra/component "0.4.0"]]

  :main ^:skip-aot component.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}})
