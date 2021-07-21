(defproject scramble "0.1.0-SNAPSHOT"

  :description "Scramblies challenge"

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [io.pedestal/pedestal.service "0.5.9"]
                 [io.pedestal/pedestal.jetty "0.5.9"]
                 [environ "1.2.0"]
                 [mount "0.1.16"]]

  :min-lein-version "2.0.0"

  :jvm-opts ["-server"]
  :source-paths ["src"]
  :resource-paths ["resources"]

  :plugins [[lein-environ "1.1.0"]
            [lein-midje "3.2.1"]]

  :main ^:skip-aot scramble.core


  :profiles {:uberjar {:omit-source  true
                       :aot          :all
                       :uberjar-name "scramble.jar"}
             :dev     {:source-paths ["test"]
                       :repl-options {:init-ns scramble.core}
                       :env          {:http-port "9090"}}
             :midje   {:dependencies [[midje "1.9.9"]
                                      [clj-http "3.10.1"]]
                       :source-paths ["test"]
                       :env          {:http-port "9191"}}})
