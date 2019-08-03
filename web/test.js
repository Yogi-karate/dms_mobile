var today = new Date();

var str_today = today.toISOString().slice(0,10);
console.log("The current month is ", today.getMonth());
console.log("The current month is ", today.getFullYear()+1);
let year=2019;
let month=8;
let current_year = parseInt(year);
let current_month = parseInt(month);
var lastDay = new Date(current_year, current_month-1, 1);
var firstDay = new Date(current_year, current_month-1,0);
console.log("The current month first day is ", lastDay.toLocaleDateString(),today,firstDay.toLocaleDateString());
var day = lastDay.toISOString().slice(0,10);
var int_date = lastDay.getDate();
console.log(" Day is ",day,int_date);
var result = {};
while (int_date != today.getDate()){
   var day = int_date <10 ? "0"+int_date:int_date;
   result[lastDay.toISOString().slice(0,8)+day]=0;
   int_date++;
}
console.log("result",result);