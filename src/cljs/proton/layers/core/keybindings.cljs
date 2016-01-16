(ns proton.layers.core.keybindings
  (:require [proton.layers.core.actions :as actions :refer [state]]
            [proton.lib.pane_manager :as panes]
            [proton.lib.atom :as atom-env]
            [proton.lib.proton :as proton]))

(defn select-window-fn [n]
  (fn []
    (panes/focus-on-item n)))

(def keybindings
  (atom { :0-9 {:title "select window N"}
          :0 {:fx (select-window-fn 0)
              :hidden true
              :title "window 0"}
          :1 {:fx (select-window-fn 1)
              :hidden true
              :title "window 1"}
          :2 {:fx (select-window-fn 2)
              :hidden true
              :title "window 2"}
          :3 {:fx (select-window-fn 3)
              :hidden true
              :title "window 3"}
          :5 {:fx (select-window-fn 4)
              :hidden true
              :title "window 4"}
          :6 {:fx (select-window-fn 6)
              :hidden true
              :title "window 6"}
          :7 {:fx (select-window-fn 7)
              :hidden true
              :title "window 7"}
          :8 {:fx (select-window-fn 8)
              :hidden true
              :title "window 8"}
          :9 {:fx (select-window-fn 9)
              :hidden true
              :title "window 9"}
          :j {:action "window:focus-pane-below"
              :target actions/get-active-pane
              :title "focus below pane"}
          :k {:action "window:focus-pane-above"
              :target actions/get-active-pane
              :title "focus above pane"}
          :l {:action "window:focus-pane-on-right"
              :target actions/get-active-pane
              :title "focus right pane"}
          :h {:action "window:focus-pane-on-left"
              :target actions/get-active-pane
              :title "focus left pane"}
          :tab {:action "tab-switcher:next"
                :title "previous buffer"}
          :space {:action "easy-motion-redux:letter"
                  :target "atom-text-editor:not([mini])"
                  :title "easy motion"}
          (keyword ":") {:action "command-palette:toggle"
                         :title "run command"}
          :f {:category "files"
               :e {:category "editor(atom)"
                   :d {:title "find-dotfile"
                       :fx (fn []
                             (.open (.-workspace js/atom) proton/config-path))}
                   :s {:title "find-stylesheet"
                       :action "application:open-your-stylesheet"}
                   :i {:title "find-init script"
                       :action "application:open-your-init-script"}
                   :p {:title "find-snippets"
                       :action "application:open-your-snippets"}}
               :f {:title "advanced-open-file"
                   :action "advanced-open-file:toggle"}
               := {:action "atom-beautify:beautify-editor" :title "format file"}}
          :w {:category "window"
              :m {:title "close others"
                  :fx panes/close-other-panes}
              :d {:action "pane:close"
                  :target actions/get-active-pane
                  :title "destroy pane"}
              :v {:action "pane:split-right"
                  :target actions/get-active-pane
                  :title "split vertically"}
              :h {:action "pane:split-down"
                  :target actions/get-active-pane
                  :title "split horizontally"}
              :V {:action "pane:split-left"
                  :target actions/get-active-pane
                  :title "split vertically and focus left"}
              :H {:action "pane:split-up"
                  :target actions/get-active-pane
                  :title "split horizontally and focus up"}}
         :b {:category "buffer"
              :b {:action "fuzzy-finder:toggle-buffer-finder"
                  :title "browse buffers"}
              :K {:action "pane:close-other-items"
                  :target actions/get-active-pane
                  :title "kill other buffers"}
              :d {:action "pane:close"
                  :target actions/get-active-pane
                  :title "destroy current buffer"}}
         :p {:category "project"
             :b {:action "fuzzy-finder:toggle-buffer-finder"
                 :title "browse buffers"}
             :t {:action "tree-view:toggle"
                 :title "tree-view"}
             :f {:action "fuzzy-finder:toggle-file-finder"
                 :title "find in project"}
             :p {:action "project-manager:list-projects"
                 :title "switch projects"}
             :s {:action "project-manager:save-project"
                 :title "save project"}
             :e {:action "project-manager:edit-projects"
                 :title "edit projects"}
             :r {:action "recent-files-fuzzy-finder:toggle-finder"
                 :title "recent files"}}
         :t {:category "toggles"
             :t {:title "tab-bar"
                 :fx (fn []
                       (if (get @state :tabs)
                        (do
                          (actions/toggle-tabs false)
                          (swap! state assoc-in [:tabs] false))
                        (do
                          (actions/toggle-tabs true)
                          (swap! state assoc-in [:tabs] true))))}
             :n {:title "line numbers"
                 :target (fn [atom] (.getView (.-views atom) (.getActiveTextEditor (.-workspace atom))))
                 :action "editor:toggle-line-numbers"}
             :i {:action "editor:toggle-indent-guide"
                 :target "atom-text-editor:not([mini])"
                 :title "indent guide"}
             :I {:action "window:toggle-auto-indent"
                 :title "auto indent"}
             :s {:title "status bar"
                 :action "status-bar:toggle"}
             :g {:title "golden ratio"
                 :action "golden-ratio:toggle"}
             :r {:title "relative numbers"
                 :fx (fn []
                       (if (get @state :relative-numbers)
                        (do
                          (actions/toggle-relative-lines false)
                          (swap! state assoc-in [:relative-numbers] false))
                        (do
                          (actions/toggle-relative-lines true)
                          (swap! state assoc-in [:relative-numbers] true))))}
              :w {:title "whitespace"
                  :fx (fn [] (atom-env/set-config! "editor.showInvisibles" (not (atom-env/get-config "editor.showInvisibles"))))}}
          :T {:category "UI toggles/themes"
              :F {:action "window:toggle-full-screen" :title "toggle full screen"}
              :M {:fx actions/toggle-maximize :title "toggle maximize"}
              :n {:action "theme-switch:next" :title "cycle themes"}
              :m {:action "window:toggle-menu-bar" :title "toggle menu bar"}}
          :m {:category "mode"}
          (keyword ";") {:action "editor:toggle-line-comments"
                         :target "atom-text-editor:not([mini])"
                         :title "comment lines"}
          :v {:action "expand-region:expand"
              :target "atom-text-editor:not([mini])"
              :title "expand region"}
          :_ {:category "meta"
              :d {:title "find-dotfile"
                  :fx (fn []
                       (.open (.-workspace js/atom) proton/config-path))}
              :R {:title "reload-editor"
                  :action "window:reload" :dom-target "body"}
              :c {:title "dev-tools"
                  :action "window:toggle-dev-tools"}
              :r {:title "reload-proton"
                  :fx (fn [] (atom-env/force-reload-package "proton-mode"))}}}))
