angular.module('gtUser').animation('.drawer', function () {
  return {
    enter: function (elem, done) {
      elem[0].parentElement.style.height = elem[0].offsetHeight + 'px';
      setTimeout(done, 400)
    },
    leave: function(elem, done) {
      if(elem[0].parentElement.childElementCount == 1) {
        elem[0].parentElement.style.height = 0;
        setTimeout(done, 400)
      }
      else {
        done();
      }
    }
  }
});
