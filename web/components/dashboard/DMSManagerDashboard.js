import React from "react";

import GridItem from "../common/Grid/GridItem.js";
import GridContainer from "../common/Grid/GridContainer.js";

import TRManagerCard from './TRManagerCard';
import TRTable from './TRTable';
import TeamDropDown from './TeamDropdown';
import withAuth from '../../lib/withAuth';
import DailyLeads from './DailyLeads';

const EnquiryCardProps = {

    cardTitle: 'Total Enquiries',
    cardIcon: 'faClipboardList',
    cardColor: 'primary',
    graphType: 'doughnut',
    progressBarColor: 'primary',
    progressBarVariant: 'determinate',
    progressBarSize: 35,
    graphData: [700, 1100, 1200],
    graphLabel: [
        'Mettuguda',
        'Tirumalgiri',
        'Nacharam',
    ],
    graphColors: [
        '#DA70D6',
        '#EE82EE',
        '#BA55D3'
    ],
    renderDropdown: false,
}


const FollowupCardProps = {

    cardTitle: 'Total Followups',
    cardColor: 'followUp',
    cardIcon: 'faBell',
    graphType: 'doughnut',
    progressBarColor: 'primary',
    progressBarVariant: 'determinate',
    progressBarSize: 35,
    graphData: [456, 70, 1200],
    graphLabel: [
        'Overdue',
        'Due Today',
        'Planned'
    ],
    graphColors: [
        '#ec407a',
        '#DE6695',
        '#F54A90'
    ],
    renderDropdown: true,

}

const BookingCardProps = {

    cardTitle: 'Bookings',
    cardColor: 'info',
    cardIcon: 'faCar',
    graphType: 'pie',
    graphData: [80, 120, 100],
    graphLabel: [
        'Mettuguda',
        'Tirumalgiri',
        'Nacharam'
    ],
    graphColors: [
        '#AFEEEE',
        '#48D1CC',
        '#00CED1'
    ],
    renderDropdown: false,

}

const SalesCardProps = {

    cardTitle: 'Inventory',
    cardColor: 'success',
    cardIcon: 'faWarehouse',
    graphType: 'pie',
    graphData: [80, 120, 100],
    graphLabel: [
        'In stock',
        'Allocation',
        'In Transit'
    ],
    graphColors: [
        '#48DA4A',
        '#3BD574',
        '#28B85D'
    ],
    renderDropdown: false,
}

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
                        <TRManagerCard {...EnquiryCardProps} ></TRManagerCard>
                    </GridItem>
                    <GridItem xs={12} sm={6} md={3}>
                        <TRManagerCard {...BookingCardProps}></TRManagerCard>
                    </GridItem>
                    <GridItem xs={12} sm={6} md={3}>
                        <TRManagerCard {...FollowupCardProps}></TRManagerCard>
                    </GridItem>
                    <GridItem xs={12} sm={6} md={3}>
                        <TRManagerCard {...SalesCardProps}></TRManagerCard>
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
