(defproject bare "0.1.0-SNAPSHOT"
  :description "Basic server process example."
  :url "https://github.com/jconti/servers"

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/spec.alpha "0.2.176"]
                 [ring "1.7.1"]
                 [seancorfield/next.jdbc "1.0.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [com.microsoft/sqljdbc4 "4.2"]]

  ;; https://github.com/technomancy/leiningen/blob/master/doc/PROFILES.md#debugging
  ;; https://github.com/technomancy/leiningen/blob/master/sample.project.clj#L215
  :main ^:skip-aot bare.core

  ;; https://github.com/technomancy/leiningen/blob/master/sample.project.clj#L309
  :target-path "target/%s"

  ;; https://github.com/technomancy/leiningen/blob/master/doc/PROFILES.md
  :profiles {:uberjar {:aot :all}})

