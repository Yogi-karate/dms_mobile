import React from "react";

import GridItem from "../common/Grid/GridItem.js";
import GridContainer from "../common/Grid/GridContainer.js";
import Grid from "@material-ui/core/Grid";

import DailyLeads from './DailyLeads';
import EnquiryCardComponent from './EnquiryComponent';
import StageCount from './EnquiryStateTable';
import FollowupsCardComponent from './FollowupsComponent';
import SalesCardComponent from './SalesComponent';
import InventoryCardComponent from './InventoryComponent';
import DailyUserCount from './DailyUserCount';
import { connect } from 'react-redux';
import PerfToolbar from './perfToolbar';

class DMSDashboard extends React.Component {
    constructor(props) {
        super(props);
        console.log("The props are in dasboar", props);
        this.props = props;
        this.state = {
            teams: [],
        };
    }
    async componentDidUpdate(prevProps) {
        console.log("the componentDidUpdate props are ", this.props.loggedIn, prevProps.loggedIn);
        if (this.props.loggedIn && this.props.loggedIn != prevProps.loggedIn) {
            console.log("changing state ----");
            this.setState({ loggedIn: this.props.loggedIn});
        }
    }
    render() {
        return (
            <GridContainer direction="column">                              
                    <Grid container>
                    <GridItem xs={12} sm={6} md={3}>
                        <EnquiryCardComponent {... this.props}/>
                    </GridItem>
                    <GridItem xs={12} sm={6} md={3}>
                        <FollowupsCardComponent {... this.props}/>
                    </GridItem>
                    <GridItem xs={12} sm={6} md={3}>
                        <SalesCardComponent {... this.props}/>
                    </GridItem>
                    <GridItem xs={12} sm={6} md={3}>
                        <InventoryCardComponent {... this.props}/>
                    </GridItem>
                    </Grid>
                    <GridItem xs={12} sm={12} md={12}>
                        <StageCount {... this.props} />
                    </GridItem>
                    <GridItem xs={12}>
                        <PerfToolbar {...this.props} />
                    </GridItem>
                    <Grid container direction="column">
                    {(this.props.showDailyLeads == null || this.props.showDailyLeads == false) ?
                        (<GridItem xs={12} sm={12} md={12}>
                            <DailyLeads {... this.props} />
                        </GridItem>) : (<GridItem xs={12} sm={12} md={12}>
                            <DailyUserCount {... this.props} />
                        </GridItem>)}
                    
                    </Grid>
            </GridContainer>
        );
    }
}

const mapStateToProps = state => {
    console.log("state in mapping", state);
    return {showDailyLeads: state.showDailyLeads };
}

export default connect(
    mapStateToProps,
    null
)(DMSDashboard);
