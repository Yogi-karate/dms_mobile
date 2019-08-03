import React from "react";
import TRManagerCard from './TRManagerCard';
import { getStageCounts } from '../../lib/api/dashboard';

class EnquiryCardComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            enquires: [],
            cardTitle: 'Enquiries',
            cardIcon: 'faClipboardList',
            cardColor: 'primary',
            graphType: 'doughnut',
            progressBarColor: 'primary',
            progressBarVariant: 'determinate',
            progressBarSize: 35,
            graphColors: [
                '#DA70D6',
                '#EE82EE',
                '#BA55D3'
            ],
            renderDropdown: true,
        };
    }
    async stageCounts() {
        try {
            console.log("The complete props ", this.props)
            const resp = await getStageCounts();
            console.log("The data from admin js", resp);
            let graphData = [];
            let graphLabel = [];
            const enquiresArray = resp.map((stage) => {
                graphLabel.push(stage.stage_id[1] + " - " + stage.stage_id_count);
                graphData.push(stage.stage_id_count);
                console.log("enquiryyyyyyy data", stage.stage_id[1]);
                return [stage.stage_id[1], stage.stage_id_count]
            });

            console.log("The data followup", enquiresArray[0]);
            return ({ enquires: enquiresArray, graphData: graphData, graphLabel: graphLabel }); // eslint-disable-line

        } catch (err) {
            console.log(err); // eslint-disable-line
        }
    }

    async componentDidMount() {
        this.setState(await this.stageCounts());
    }

    render() {
        return (
            <div>
                <TRManagerCard {...this.state} ></TRManagerCard>
            </div>
        );
    }
}

export default EnquiryCardComponent;
