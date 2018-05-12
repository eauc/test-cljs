(ns build
  (:require [cljs.build.api :as b]
            [cider-nrepl.main :as crepl]
            [figwheel-sidecar.repl-api :as ra]
            [clojure.java.shell :refer [sh]]))

(def source-path "src")

(def dev-options
  {:main 'hello-world.core
   :output-to "resources/public/js/main.js"
   :output-dir "resources/public/js/out"
   :asset-path "js/out"
   :optimizations :none
   :verbose true})

(def figwheel-options
  {:figwheel-options {}
   :build-ids ["dev"]
   :all-builds
   [{:id "dev"
     :figwheel true
     :source-paths [source-path]
     :compiler dev-options}]})

(defn start-repl
  []
  (ra/start-figwheel! figwheel-options)
  (ra/cljs-repl))

(defn clean
  []
  (println
    (:out
     (sh "rm" "-rfv" "resources/public/js"))))

(defmulti build
  (fn [name] name))

(defmethod build :default
  [name & rest]
  (println "Unknown \"" name "\" profile"))

(defmethod build "clean"
  [& args]
  (println "Cleaning project...")
  (clean))

(defmethod build "dev"
  [& args]
  (println "Building \"dev\" profile...")
  (b/build source-path dev-options))

(defmethod build "repl"
  [& args]
  (println "Lauching figwheel repl...")
  (start-repl))

(defmethod build "cider"
  [& args]
  (println "Lauching cider nREPL...")
  (crepl/init ["cider.nrepl/cider-middleware"
               "refactor-nrepl.middleware/wrap-refactor"
               "cider.piggieback/wrap-cljs-repl"]))

(defn -main [& args]
  (apply build (or args '("dev"))))
