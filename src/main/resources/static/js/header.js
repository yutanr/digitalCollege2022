$(function(){
  $(".has-sub").mouseover(function(){
    $(this).children(".sub").stop().slideDown();
  });
  $(".has-sub").mouseout(function(){
    $(".sub").stop().slideUp();
  });
});