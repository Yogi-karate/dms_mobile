import React from "react";

import GridItem from "../common/Grid/GridItem.js";
import GridContainer from "../common/Grid/GridContainer.js";
import Grid from "@material-ui/core/Grid";

import TeamDropDown from './TeamDropdown';
import withAuth from '../../lib/withAuth';
import DailyLeads from './DailyLeads';
import EnquiryCardComponent from './EnquiryComponent';
import StageCount from './EnquiryStateTable';
import FollowupsCardComponent from './FollowupsComponent';
import SalesCardComponent from './SalesComponent';
import InventoryCardComponent from './InventoryComponent';
import DailyUserCount from './DailyUserCount';
import { connect } from 'react-redux';

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
            <GridContainer direction="column">           
                    <GridItem xs={12}>
                        <TeamDropDown {...this.props} />
                    </GridItem>
                    <Grid container>
                    <GridItem xs={12} sm={12} md={3}>
                        <EnquiryCardComponent></EnquiryCardComponent>
                    </GridItem>
                    <GridItem xs={12} sm={12} md={3}>
                        <FollowupsCardComponent></FollowupsCardComponent>
                    </GridItem>
                    <GridItem xs={12} sm={12} md={3}>
                        <SalesCardComponent></SalesCardComponent>
                    </GridItem>
                    <GridItem xs={12} sm={12} md={3}>
                        <InventoryCardComponent></InventoryCardComponent>
                    </GridItem>
                    </Grid>
                    <Grid container>
                    {(this.props.showDailyLeads == null || this.props.showDailyLeads == false) ?
                        (<GridItem xs={12} sm={12} md={6}>
                            <DailyLeads {... this.props} />
                        </GridItem>) : (<GridItem xs={12} sm={12} md={6}>
                            <DailyUserCount {... this.props} />
                        </GridItem>)}
                    <GridItem xs={12} sm={12} md={6}>
                        <StageCount {... this.props} />
                    </GridItem>
                    </Grid>
            </GridContainer>
        );
    }
}

const mapStateToProps = state => {
    console.log("state in mapping", state);
    return { dailyLeadsUser: state.dailyleads_userIndex, showDailyLeads: state.showDailyLeads };
}

export default connect(
    mapStateToProps,
    null
)(withAuth(DMSDashboard));
