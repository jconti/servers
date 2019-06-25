(ns bare.init
  (:require
   [bare.db :as db]
   [bare.global :as global]
   [bare.web :as web]
   [integrant.core :as ig]))

(defn read-config
  "Read program configuration"
  []
  ;; see https://12factor.net/config
  {:adapter/jetty    {:port (if-let [port-str (System/getenv "HTTP_PORT")]
                              (Long/parseLong port-str)
                              4000)
                      :db   (ig/ref :database/custody)}
   :database/custody {:host   (System/getenv "DB_HOST")
                      :dbname (System/getenv "DB_NAME")}})

(defn init!
  "Transform a program-config into a running system."
  [program-config]
  (let [system (ig/init program-config)]
    (reset! global/state system))
  nil)

(defn shutdown!
  "Stop a running system."
  []
  (when-let [system @global/state]
    (ig/halt! system)
    (reset! global/state nil))
  nil)
