(binding [*compile-files* true]
  (require 'clojure.test))

(ns test-runner
  (:require [clojure.test :as t]))

(when-not (.exists (java.io.File. "classes"))
  (.. (java.io.File. "classes") mkdir))

(defmulti emacs-report :type)

(defmethod emacs-report :pass
  [m]
  (t/with-test-out
    (println "PASS:")))

(defn -main  [& [test-file]]
  (binding  [*compile-files* true]
    (compile 'test-runner)
    (let  [test-namespaces (->> (or test-file  "test")
                                (java.io.File.)
                                (file-seq)
                                (filter (memfn isFile))
                                (map (memfn getPath))
                                (map load-file)
                                (map (comp :ns meta))
                                (into #{}))]
      (run! (fn [n] (t/run-tests n)) test-namespaces))))
