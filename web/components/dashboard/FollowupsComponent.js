import React from "react";
import TRManagerCard from './TRManagerCard';
import TRTable from './TRTable';
import TRCustomDropDown from '../common/Dropdown';
import { getDashboard } from '../../lib/api/dashboard';
import { state_select } from '../../lib/store';
import { connect } from 'react-redux';


class FollowupsCardComponent extends React.Component {
    constructor(props) {
        super(props);
        this.followupClick = this.followupClick.bind(this);

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
            renderDropdown: false,
            onClick: this.followupClick,
        };
    }

    followupClick(evt, item) {
        if (item[0] != undefined && item[0] != null) {
            console.log('onclick piechart item', item[0]._model.label);
            this.props.state_select(item[0]._model.label);
        }
    }
    async componentDidMount() {
        try {
            console.log("The complete propsssssssssssssssssssssssssssssssssssssssssssssssss ", this.props)
            const resp = await getDashboard();
            console.log("The data from admin js", resp);
            let graphData = [];
            let graphLabel = [];
            const followUpArray = resp.map((record) => {
                graphLabel.push(record.state);

                const stageTotalCount = record.result.reduce(function (acc, stage) {
                    console.log("The reduce is ", stage.stage_id_count);
                    return stage.stage_id_count + acc;
                }, 0);//add and get each staged total stage_id_count (eg:overdue,planned,today). 

                graphData.push(stageTotalCount);
                console.log("The stage array is ", stageTotalCount);
                console.log("followups data", record);
                return [record.state, record.result, stageTotalCount]
            });

            console.log("The data followups", followUpArray[0]);
            this.setState({ followups: followUpArray, graphData: graphData, graphLabel: graphLabel }); // eslint-disable-line

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

const mapDispatchToProps = { state_select }
export default connect(
    null,
    mapDispatchToProps
)(FollowupsCardComponent);
