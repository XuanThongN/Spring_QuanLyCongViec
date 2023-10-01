// (function ($) {
    //serializeFormToObject plugin for jQuery
    $.fn.serializeFormToObject = function (camelCased = false) {
        //serialize to array
        var data = $(this).serializeArray();

        //add also disabled items
        $(':disabled[name]', this).each(function () {
            data.push({ name: this.name, value: $(this).val() });
        });

        //map to object
        var obj = {};
        data.map(function (x) { obj[x.name] = x.value; });

        if (camelCased && camelCased === true) {
            return convertToCamelCasedObject(obj);
        }

        //những input có format là tiền thì phải convert lại thành số
        this.find('.input-amount').toArray().forEach(function (field) {
            obj[field.name] = field.value.replaceAll(".", "")
        });

        return obj;
    };

    //Configure validator
    // $.validator.setDefaults({
    //     highlight: (el) => {
    //         $(el).addClass('is-invalid');
    //     },
    //     unhighlight: (el) => {
    //         $(el).removeClass('is-invalid');
    //     },
    //     errorElement: 'p',
    //     errorClass: 'text-danger',
    //     errorPlacement: (error, element) => {
    //         if (element.parent('.input-group').length) {
    //             error.insertAfter(element.parent());
    //         } else {
    //             error.insertAfter(element);
    //         }
    //     }
    // });


    $.fn.clearForm = function () {
        var $this = $(this);
        $this.validate().resetForm();
        $('[name]', $this).each((i, obj) => {
            $(obj).removeClass('is-invalid');
        });
        $this[0].reset();
    };



//init summernote
    initSummernote = function initSummernote(value) {
        value.summernote(
            {
                height: 300,
                toolbar: [
                    ['style', ['style']],
                    ['font', ['bold', 'underline', 'clear']],
                    ['fontsize', ['fontsize']],
                    ['fontname', ['fontname']],
                    ['color', ['color']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['table', ['table']],
                    ['insert', ['link', 'picture']],
                    ['view', ['fullscreen', 'codeview', 'help']],
                ],
                dialogsInBody: true,
                callbacks: {
                    // onImageUpload: function (image) {
                    //     uploadImage(image[0], $(this),'CauHoi');
                    // }
                }
            }
        );
    }


    function convertToCamelCasedObject(obj) {
        var newObj, origKey, newKey, value;
        if (obj instanceof Array) {
            return obj.map(value => {
                if (typeof value === 'object') {
                    value = convertToCamelCasedObject(value);
                }
                return value;
            });
        } else {
            newObj = {};
            for (origKey in obj) {
                if (obj.hasOwnProperty(origKey)) {
                    newKey = (
                        origKey.charAt(0).toLowerCase() + origKey.slice(1) || origKey
                    ).toString();
                    value = obj[origKey];
                    if (
                        value instanceof Array ||
                        (value !== null && value.constructor === Object)
                    ) {
                        value = convertToCamelCasedObject(value);
                    }
                    newObj[newKey] = value;
                }
            }
        }
        return newObj;
    }


// })