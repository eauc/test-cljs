(ns repl
  (:require [cider-nrepl.main :as crepl]
            [figwheel-sidecar.repl-api :as ra]
            [build :as b]))

(def figwheel-options
  {:figwheel-options {}
   :build-ids ["dev"]
   :all-builds
   [{:id "dev"
     :figwheel true
     :source-paths [b/source-path]
     :compiler b/dev-options}]})

(defn fw-start
  []
  (ra/start-figwheel! figwheel-options))

(defn fw-repl
  []
  (ra/cljs-repl))

(defn -main
  []
  (crepl/init ["cider.nrepl/cider-middleware"
               "refactor-nrepl.middleware/wrap-refactor"
               "cider.piggieback/wrap-cljs-repl"]))
