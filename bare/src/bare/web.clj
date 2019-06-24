(ns bare.web
  (:require
   [bare.logic :as logic]
   [com.stuartsierra.component :as component]
   [ring.adapter.jetty :as jetty]))

(defn start
  [{:keys [configuration server db] :as this}]
  (if server
    this
    (let [handler (logic/make-handler db)]
      (assoc this
             :handler handler
             :server (jetty/run-jetty handler configuration)))))

(defn stop
  [{:keys [server] :as this}]
  (if-not server
    this
    (do (.stop server)
        (dissoc this :handler :server))))

(defn web-config
  "Make an options map for ring.jetty/run-jetty from a program-config"
  [{:keys [http-port] :as program-config}]
  {:port (or http-port 4000) :join? false})

(defn create
  [configuration]
  (with-meta
    {:configuration (web-config configuration)}
    {`component/start start
     `component/stop stop}))
