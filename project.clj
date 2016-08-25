(defproject clj-smartystreets "0.1.6"
  :description "A Clojure library wrapping SmartyStreets' LiveAddress API."
  :url "https://github.com/democracyworks/clj-smartystreets"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :lein-min-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.1.0"]
                 [cheshire "5.6.3"]]
  :profiles {:dev {:dependencies [[midje "1.8.3"]]}}
  :plugins [[lein-midje "3.2"]]
  :aliases {"test" ["with-profile" "+test" "midje"]}
  :deploy-repositories {"releases" :clojars})
