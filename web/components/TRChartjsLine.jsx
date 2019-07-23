import React, { Component } from "react";
import {Line} from 'react-chartjs-2';
import {Chart} from 'chart.js';

Chart.defaults.global.elements.line.borderWidth = 1;
const data = {
  labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
  datasets: [
    {
      label: 'Quotes',
      fill: true,
      lineTension: .5,
      backgroundColor: 'rgba(134, 23, 195,0.4)',
      borderColor: 'rgba(88, 103, 195,0.7)',
      pointBorderWidth: .1,
      borderCapStyle: 'butt',
      borderDash: [],
      borderDashOffset: 0.0,
      borderJoinStyle: 'miter',
      pointBorderColor: 'rgba(163,206,227,1)',
      pointBackgroundColor: '#F54A90',
      pointBorderWidth: .5,
      pointHoverRadius: 5,
      pointHoverBackgroundColor: 'rgba(163,206,227,1)',
      pointHoverBorderColor: 'rgba(88, 103, 195,0.7)',
      pointHoverBorderWidth: 1,
      pointRadius: 1,
      pointHitRadius: 10,
      data: [65, 59, 80, 81, 56, 55, 40]
      
    },
    {
        label: 'Invoices',
        fill: true,
        lineTension: 0.5,
        backgroundColor: 'rgba(28, 134, 191,0.4)',
        borderColor: 'rgba(28, 134, 191,0.7)',
        borderCapStyle: 'butt',
        borderDash: [],
        borderDashOffset: 0.0,
        borderJoinStyle: 'miter',
        pointBorderColor: 'rgba(28, 134, 191,0.7)',
        pointBackgroundColor: '#F54A90',
        pointBorderWidth: .5,
        pointHoverRadius: 5,
        pointHoverBackgroundColor: 'rgba(206,162,229,1)',
        pointHoverBorderColor: 'rgba(220,220,220,1)',
        pointHoverBorderWidth: 1,
        pointRadius: 1,
        pointHitRadius: 10,
        data: [42, 65, 76, 85, 42, 50, 50]
        
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
class TRChartjsLine extends Component {

    render() {
        return (
                <div>
                    <Line data={data} options={options} width={730} height={330}/>
                </div>
        );
    };
}

export default TRChartjsLine;