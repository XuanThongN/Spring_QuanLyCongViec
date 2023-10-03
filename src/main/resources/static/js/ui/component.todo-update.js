!function (t) {
    "use strict";

    function o() {
        this.$body = t("body #task-update-modal"),
            this.$todoContainer = t("#task-update-modal #todo-container"),
            this.$todoMessage = t("#task-update-modal #todo-message"),
            this.$todoRemaining = t("#task-update-modal #todo-remaining"),
            this.$todoTotal = t("#task-update-modal #todo-total"),
            this.$archiveBtn = t("#task-update-modal #btn-archive"),
            this.$todoList = t("#task-update-modal #todo-list"),
            this.$todoDonechk = ".todo-done",
            this.$todoForm = t("#task-update-modal #todo-update-form"),
            this.$todoInput = t("#task-update-modal #todo-input-text"),
            this.$todoBtn = t("#task-update-modal #todo-btn-submit"),
            this.$todoRemoveBtn = "#task-update-modal .todo-remove",
            this.$todoData = [],
            this.$todoCompletedData = [],
            this.$todoUnCompletedData = []
    }

    o.prototype.markTodo = function (t, o) {
        for (var e = 0; e < this.$todoData.length; e++)
            this.$todoData[e].id == t && (this.$todoData[e].done = o)
    }
        ,
        o.prototype.addTodo = function (t) {
            this.$todoData.push({
                id: this.$todoData.length > 0 ? this.$todoData[this.$todoData.length - 1].id + 1: this.$todoData.length,
                text: t,
                done: !1
            }),
                this.generate()
        }
        ,
        o.prototype.archives = function () {
            this.$todoUnCompletedData = [];
            for (var t = 0; t < this.$todoData.length; t++) {
                var o = this.$todoData[t];
                1 == o.done ? this.$todoCompletedData.push(o) : this.$todoUnCompletedData.push(o)
            }
            this.$todoData = [],
                this.$todoData = [].concat(this.$todoUnCompletedData),
                this.generate()
        }
        ,
        o.prototype.generate = function () {
            this.$todoList.html("");
            for (var t = 0, o = 0; o < this.$todoData.length; o++) {
                var e = this.$todoData[o];
                // Create a button to remove the to-do item
                var removeButton = '<button type="button" class="btn btn-sm btn-outline-danger float-end todo-remove" data-id="' + e.id + '" style="padding: .2rem .5rem"><i class="mdi mdi-window-close"></i> </button>';
                1 == e.done ? this.$todoList.prepend('<li class="list-group-item border-0 ps-0"><div class="form-check mb-0"><input type="checkbox" class="form-check-input todo-done" id="' + e.id + '" checked><label class="form-check-label" for="' + e.id + '"><s>' + e.text + "</s></label>" + removeButton + "</div></li>") : (t += 1,
                    this.$todoList.prepend('<li class="list-group-item border-0 ps-0"><div class="form-check mb-0"><input type="checkbox" class="form-check-input todo-done" id="' + e.id + '"><label class="form-check-label" for="' + e.id + '">' + e.text + "</label>" + removeButton + "</div></li>"))
            }
            this.$todoTotal.text(this.$todoData.length),
                this.$todoRemaining.text(t)
        }
        ,
        o.prototype.init = function () {
            var o = this;
            this.generate(),
                this.$archiveBtn.on("click", function (t) {
                    return t.preventDefault(),
                        o.archives(),
                        !1
                }),
                t(document).on("change", this.$todoDonechk, function () {
                    this.checked ? o.markTodo(t(this).attr("id"), !0) : o.markTodo(t(this).attr("id"), !1),
                        o.generate()
                }),
                this.$todoForm.on("submit", function (t) {
                    return t.preventDefault(),
                        "" == o.$todoInput.val() || void 0 === o.$todoInput.val() || null == o.$todoInput.val() ? (o.$todoInput.focus(),
                            !1) : (o.addTodo(o.$todoInput.val()),
                            o.$todoForm.removeClass("was-validated"),
                            o.$todoInput.val(""),
                            !0)
                }),
                t(document).on("click", this.$todoRemoveBtn, function () {
                    var id = $(this).data('id');
                    o.$todoData = o.$todoData.filter(function (item) {
                        return item.id !== id;
                    })
                    o.generate()
                })
        }
        ,
        t.TodoAppUpdate = new o,
        t.TodoAppUpdate.Constructor = o
}(window.jQuery),
    function () {
        "use strict";
        window.jQuery.TodoAppUpdate.init()
    }();