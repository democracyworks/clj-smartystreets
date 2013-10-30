(defproject clj-smartystreets "0.1.1"
  :description "A Clojure library wrapping SmartyStreets' LiveAddress API."
  :url "https://github.com/turbovote/clj-smartystreets"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :lein-min-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.7.6"]
                 [midje "1.5.1"]]
  :plugins [[lein-midje "3.1.0"]]
  :aliases {"test" ["with-profile" "+test" "midje"]})
