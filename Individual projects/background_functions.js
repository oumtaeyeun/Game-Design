
// 
// Change the background screen color to blue.
//
function blue() {
  document.bgColor = "#0000f0";
  Fill = "#0000f0";
}


// 
// Change the background screen color to green.
//
function green() {
  document.bgColor = "#0f6000";
  Fill = "#0f6000";
}


// 
// Change the background screen color to red.
//
function red() {
  document.bgColor = "#f04040";
  Fill = "#f04040";
}


function loadComplete() {
  console.log("Load is complete."); 
  blue();
  //red();
}


