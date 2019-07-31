import React from "react";
import TRManagerCard from './TRManagerCard';
import TRTable from './TRTable';
import TRCustomDropDown from '../common/Dropdown';
import { getDashboard } from '../../lib/api/dashboard';
import { enquiry_stage_change } from '../../lib/store';
import { connect } from 'react-redux';

class EnquiryCardComponent extends React.Component {
    constructor(props) {
        super(props);
        this.enquiryClick = this.enquiryClick.bind(this);

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
            renderDropdown: false,
            onClick:this.enquiryClick,
        };
    }
    enquiryClick(evt, item) {
        if(item[0] != undefined && item[0] != null){
        console.log('onclick piechart item', item[0]._model.label);
        this.props.enquiry_stage_change(item[0]._model.label);
        }
    }
    async componentDidMount() {
        try {
            console.log("The complete propsssssssssssssssssssssssssssssssssssssssssssssssss ", this.props)
            const resp = await getDashboard();
            console.log("The data from admin js", resp);
            let graphData = [];
            let graphLabel = [];
            const enquiryArray = resp.map((enquiry) => {
                graphLabel.push(enquiry.state);

                const stageTotalCount = enquiry.result.reduce(function (acc, stage) {
                    console.log("The reduce is ", stage.stage_id_count);
                    return stage.stage_id_count + acc;
                }, 0);//add and get each staged total stage_id_count (eg:overdue,planned,today). 

                graphData.push(stageTotalCount);
                console.log("The stage array is ", stageTotalCount);
                console.log("enquiry data", enquiry);
                return [enquiry.state, enquiry.result, stageTotalCount]
            });

            console.log("The data enquiry", enquiryArray[0]);
            this.setState({ enquires: enquiryArray, graphData: graphData, graphLabel: graphLabel }); // eslint-disable-line

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

const mapDispatchToProps = { enquiry_stage_change }
export default connect(
    null,
    mapDispatchToProps
)(EnquiryCardComponent);
