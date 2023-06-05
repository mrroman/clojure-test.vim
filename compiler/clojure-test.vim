vim9script
if exists("g:current_compiler")
    finish
endif

g:current_compiler = "clojure-test"

if exists(":CompilerSet") != 2
    command -nargs=* CompilerSet setlocal <args>
endif

echomsg expand('<sfile>:p:h:h')

var clojure_test_deps = "'{:aliases {:clojure-test-vim {:extra-paths [\"classes\"] :extra-deps {mrroman/clojure-test.vim {:local/root \"" .. expand('<sfile>:p:h:h') .. "/clojure\"}}}}}'"

execute 'CompilerSet makeprg=clojure\ -Sdeps\ ' .. escape(clojure_test_deps, ' \|"') .. '\ -Mtest:clojure-test-vim\ -m\ test-runner'
CompilerSet errorformat&
