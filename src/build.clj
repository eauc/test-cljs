(ns build
  (:require [cljs.build.api :as b]))

(def source-path "src")

(def dev-options
  {:main 'hello-world.core
   :output-to "resources/public/js/main.js"
   :output-dir "resources/public/js/out"
   :asset-path "js/out"
   :optimizations :none
   :verbose true})

(defmulti build
  (fn [name] name))

(defmethod build :default
  [name & rest]
  (println "Unknown \"" name "\" profile"))

(defmethod build "dev"
  [& args]
  (println "Building \"dev\" profile...")
  (b/build source-path dev-options))

(defn -main [& args]
  (apply build (or args '("dev"))))
