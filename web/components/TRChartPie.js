import React, {Component} from 'react';
import {Pie} from 'react-chartjs-2';
import { defaults } from 'react-chartjs-2';

// defaults.global.legend.display = false;



class TRChartPie extends Component {

    render() {

		const graphData = this.props.graphData;
		const graphLabel = this.props.graphLabel;
		const graphColors = this.props.graphColors;

		const options = {
		
			maintainAspectRatio: false,
			legend: {
				position: 'right',
				labels: {
				  boxWidth: 10
				}
			  }
		
		}

		const data = {
			labels: graphLabel,
			datasets: [{
				data: graphData,
				backgroundColor: graphColors,
				hoverBackgroundColor: graphColors,
			}]
		};

        return (
                <div>
					<div>
						<Pie 
							data={data}
							width={150}
							height={150}
							options={options}	
						/>
					</div>
                </div>
                );
    };
}

export default TRChartPie;