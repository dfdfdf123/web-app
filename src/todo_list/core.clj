(ns todo-list.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]))

(defn welcome
  [request]
  {:status 200
   :body "<h1>Hello, World!"
   :headers {}})

(defn hello
  [request]
  (let [name (get-in request [:route-params :name])]
    {:status 200
     :body (str "Hello, " name "!")
     :headers {}}))

(defn goodbuy
  [request]
  {:status 200
   :body "<h1>Goodbuy!</h1>"
   :headers {}})

(defroutes app
  (GET "/" [] welcome)
  (GET "/goodbuy" [] goodbuy)
  (GET "/hello/:name" [] hello)
  (not-found "<h1>Page not found</h1>"))

(defn -main
  "A very simple web server using Ring & Jetty"
  [port-number]
  (jetty/run-jetty app
    {:port (Integer. port-number)}))

(defn -dev-main
  "A very simple web server using Ring & Jetty that reloads code changes via the development profile of Leiningen"
  [port-number]
  (jetty/run-jetty (wrap-reload #'app)
     {:port (Integer. port-number)}))
