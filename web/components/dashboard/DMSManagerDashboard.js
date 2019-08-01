import React from "react";

import GridItem from "../common/Grid/GridItem.js";
import GridContainer from "../common/Grid/GridContainer.js";

import TeamDropDown from './TeamDropdown';
import withAuth from '../../lib/withAuth';
import DailyLeads from './DailyLeads';
import EnquiryCardComponent from './EnquiryComponent';
import StageCount from './EnquiryStateTable';
import FollowupsCardComponent from './FollowupsComponent';
import SalesCardComponent from './SalesComponent';
import InventoryCardComponent from './InventoryComponent';

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
                    <GridItem xs={12}>
                        <TeamDropDown {...this.props} />
                    </GridItem>
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
                    <GridItem xs={12} sm={6} md={6}>
                        <DailyLeads {... this.props} />
                    </GridItem>
                    <GridItem xs={12} sm={6} md={6}>
                        <StageCount {... this.props} />
                    </GridItem>
            </GridContainer>
        );
    }
}

export default withAuth(DMSDashboard);
