(ns scramble.core
  (:require [mount.core :as mount]
            ;; mount components
            [scramble.server :as http-server])
  (:gen-class))


(defn- stop-app []
  (prn "System is shutting down...")
  (doseq [component (:stopped (mount/stop))]
    (prn component "stopped"))
  (prn "System shutdown complete. Bye.")
  (shutdown-agents))


(defn- start-app [_]
  (prn "System init...")
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app))
  (doseq [component (:started (mount/start))]
    (prn component "started")))


(defn -main [& args]
  (start-app args))

#_(start-app "")