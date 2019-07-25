import React from "react";
import TRManagerCard from './TRManagerCard';
import TRTable from './TRTable';
import TRCustomDropDown from '../common/Dropdown';
import { getDashboard } from '../../lib/api/admin';


class FollowupsCardComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            followups: [],
            cardTitle: 'Followups',
            cardColor: 'followUp',
            cardIcon: 'faBell',
            graphType: 'doughnut',
            progressBarColor: 'primary',
            progressBarVariant: 'determinate',
            progressBarSize: 35,
            graphColors: [
                '#ec407a',
                '#DE6695',
                '#F54A90'
            ],
            renderDropdown: true,
        };
    }

    async componentDidMount() {
        try {
            console.log("The complete props ", this.props)
            const resp = await getDashboard();
            console.log("The data from admin js", resp);
            let graphData = [];
            let graphLabel = [];
            let stateStageArray = [];
            const followupsArray = resp.map((followup) => {

                const stageTotalCount = followup.result.reduce(function (acc, stage) {
                    console.log("The reduce is ", stage.stage_id_count);
                    return stage.stage_id_count + acc;
                }, 0);//add and get each staged total stage_id_count (eg:overdue,planned,today). 

                const eachStageStateCount = followup.result.map((eachState) => {
                    return {[eachState.stage_id[1]]:eachState.stage_id_count};
                }); 

                stateStageArray.push(eachStageStateCount);
                graphLabel.push(followup.state);
                graphData.push(stageTotalCount);
                console.log("The stage array is ", stageTotalCount);
                console.log("followup data", followup);
                return [followup.state, followup.result, stageTotalCount, stateStageArray]
            });

            console.log("The data followup", followupsArray[0]);
            this.setState({ followups: followupsArray, graphData: graphData, graphLabel: graphLabel }); // eslint-disable-line

        } catch (err) {
            console.log(err); // eslint-disable-line
        }
    }

    render() {
        let customTeamDropdownRender;
        let customCompanyDropdownRender;

        return (
            <div>
                <div class="pure-g">
                    <div class="pure-u-1-4">
                        <TRManagerCard {...this.state} ></TRManagerCard>
                    </div>
                </div>
            </div>
        );
    }
}

export default FollowupsCardComponent;
