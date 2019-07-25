import React from "react";

import GridItem from "../common/Grid/GridItem.js";
import GridContainer from "../common/Grid/GridContainer.js";

import TRManagerCard from './TRManagerCard';
import TRTable from './TRTable';
import TeamDropDown from './TeamDropdown';
import withAuth from '../../lib/withAuth';
import DailyLeads from './DailyLeads';
import EnquiryCardComponent from './EnquiryComponent';
import FollowupsCardComponent from './FollowupsComponent';
import SalesCardComponent from './SalesComponent';
import InventoryCardComponent from './InventoryComponent';

const dropDownlistProps = {

}

class DMSDashboard extends React.Component {
    constructor(props) {
        super(props);
        console.log("The props are in dasboar", props);
        this.props = props;
        this.state = {
            teams: [],
        };
    }
    render() {
        return (
            <GridContainer>
                <GridContainer>
                    <GridItem xs={12} sm={6} md={3}>
                        <TeamDropDown {...this.props} />
                    </GridItem>
                </GridContainer>
                <GridContainer>
                    <GridItem xs={12} sm={6} md={3}>
                        <EnquiryCardComponent></EnquiryCardComponent>
                    </GridItem>
                    <GridItem xs={12} sm={6} md={3}>
                        <SalesCardComponent></SalesCardComponent>
                    </GridItem>
                    <GridItem xs={12} sm={6} md={3}>
                        <FollowupsCardComponent></FollowupsCardComponent>
                    </GridItem>
                    <GridItem xs={12} sm={6} md={3}>
                        <InventoryCardComponent></InventoryCardComponent>
                    </GridItem>
                </GridContainer>
                <GridContainer>
                    <GridItem xs={24} sm={16} md={45}>
                        <DailyLeads {... this.props} />
                    </GridItem>
                </GridContainer>
            </GridContainer>
        );
    }
}

export default withAuth(DMSDashboard);
