(ns build
  (:require [cljs.build.api :as b]))

(defn -main [& args]
  (println "Building project..." args)
  (b/build "src"
           {:main 'hello-world.core
            :output-to "out/main.js"
            :output-dir "out"
            :optimizations :none}))
