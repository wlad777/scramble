(ns scramble.server
  (:require [clojure.stacktrace :refer [print-stack-trace]]
            [io.pedestal.http :as http]
            [mount.core :refer [defstate]]
            [environ.core :refer [env]]))


(defn scramble?
  "returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false"
  [str1 str2]
  (let [chars-map1 (frequencies (or str1 ""))
        chars-map2 (frequencies (or str2 ""))]
    (every? (fn [[c freq]]
              (<= freq (get chars-map1 c 0)))
            chars-map2)))


(defn- scramble-path [{params :path-params}]
  {:status  200
   :body    (str (scramble? (:str1 params) (:str2 params)))})


(def routes #{["/scramble/:str1/:str2" :get  `scramble-path]})


(defn server-settings [port]
  {::http/routes          routes
   ::http/type            :jetty
   ::http/host            "0.0.0.0"
   ::http/port            port
   ::http/allowed-origins (constantly true)
   ::http/join?           false})


(defn- port-number [port]
  (or (cond
        (number? port) port
        (string? port) (try (Long/parseLong port) (catch Exception _ nil))
        :else nil)
      9090))


(defn- start-server [port]
  (try
    (let [port' (port-number port)]
      (prn (str "Start server on port " port'))
      (-> (server-settings port')
          (http/create-server)
          (http/start)))
    (catch Exception e
      (prn (print-stack-trace e)))))


(defn- stop-server [http-server]
  (when http-server
    (http/stop http-server)))


(defstate http-server
  :start (start-server (env :http-port))
  :stop (stop-server http-server))


