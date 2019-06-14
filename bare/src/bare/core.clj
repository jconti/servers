(ns bare.core
  (:require
   [clojure.pprint :as pp]
   [bare.init :as init]
   [taoensso.timbre :as log])
  (:gen-class))

(defn -main
  "Main entry point for program"
  [& _]
  (let [config (init/read-config)]
    (log/infof "System configuration:\n%s"
               (with-out-str (pp/pprint config)))
    (init/init! config)
    (log/info "System initialized")))
