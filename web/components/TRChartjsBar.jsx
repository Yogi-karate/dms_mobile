import React, { Component } from "react";
import {Bar} from 'react-chartjs-2';

Chart.defaults.global.elements.line.borderWidth = 1;
const data = {
  labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
  datasets: [
    {
      label: 'In Stock',
      backgroundColor: 'rgba(150,130,214,0.2)',
      borderColor: 'rgba(150,130,214,1)',
      borderWidth: 1,
      hoverBackgroundColor: 'rgba(59,154,240,0.4)',
      hoverBorderColor: 'rgba(255,99,132,1)',
      data: [65, 59, 80, 81, 56, 55, 40]
    },
    {
      label: 'To be Allocated',
      backgroundColor: 'rgba(163,206,227,0.2)',
      borderColor: 'rgba(255,99,132,1)',
      borderWidth: 1,
      hoverBackgroundColor: 'rgba(245,74,144,0.4)',
      hoverBorderColor: 'rgba(255,99,132,1)',
      data: [65, 59, 80, 81, 56, 55, 40]
    }
  ]
};

var options = {
    scales: {
              yAxes: [{
                  ticks: {
                      beginAtZero:true
                  },
                  scaleLabel: {
                       display: true,
                       labelString: 'Count',
                       fontSize: 20 
                    }
              }]            
          }  
  };
class TRChartjsBar extends Component {

    render() {
        return (
                <div>
                  <Bar
                    data={data}
                    width={700}
                    height={300}
                    options={{
                      maintainAspectRatio: false
                    }}
                  />
                </div>
                );
    };
}

export default TRChartjsBar;