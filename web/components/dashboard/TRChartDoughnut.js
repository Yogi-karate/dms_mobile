import React, { Component } from 'react';
import { Doughnut } from 'react-chartjs-2';

class TRChartDoughnut extends Component {


	render() {

		console.log("Inside doughnut..", this.props.graphData);
		const graphData = this.props.graphData;
		const graphLabel = this.props.graphLabel;
		const graphColors = this.props.graphColors;
		const data = {
			labels: graphLabel,
			datasets: [{
				data: graphData,
				backgroundColor: graphColors,
				hoverBackgroundColor: graphColors,
			}],
		};
		const options = {

			maintainAspectRatio: false,
			legend: {
				position: 'right',
				labels: {
					boxWidth: 10
				}
			}/* ,
			'onClick': function (evt, item) {
				console.log('legend onClick', evt);
				console.log('legd item', item);
			} */

		}
		options.onClick = this.props.onclick;
		return (
			<div>
				<div>
					<Doughnut
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

export default TRChartDoughnut;