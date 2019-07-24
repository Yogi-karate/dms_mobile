import React from "react";
import TRManagerCard from './TRManagerCard';
import TRTable from './TRTable';
import TRCustomDropDown from '../common/Dropdown';

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
    renderDropdown :false,
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
    renderDropdown :true,
    
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
     renderDropdown :false,
    
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
     renderDropdown :false,
}

const dropDownlistProps = {

}

class DMSDashboard extends React.Component {

  render() {
    // const { classes } = this.props;
    let customTeamDropdownRender;
    let customCompanyDropdownRender;

    customCompanyDropdownRender = <TRCustomDropDown
                                dropdown
                                dropdownHeader="ALL TEAMS"
                                buttonText="ALL TEAMS"
                                buttonProps={{
                                round: true,
                                color: "success"
                                }}
                                dropdownList={[
                                "Showroom Narayanaguda",
                                "Amberpet Team",
                                {divider: true},
                                "Separated link",
                                {divider: true},
                                "One more separated link",
                                ]}
                            />    

    customTeamDropdownRender = <TRCustomDropDown
                                dropup
                                dropdownHeader="ALL TEAMS"
                                buttonText="ALL TEAMS"
                                buttonProps={{
                                round: true,
                                color: "info"
                                }}
                                dropdownList={[
                                "Showroom Narayanaguda",
                                "Amberpet Team",
                                {divider: true},
                                "Separated link",
                                {divider: true},
                                "One more separated link",
                                ]}
                            />
    return (
        <div>
            <div class="pure-g">
                <div class="pure-u-1-4">
                    
                </div>
                <div class="pure-u-1-4">
                    
                </div>
                <div class="pure-u-1-4">
                    
                </div>
                <div class="pure-u-1-4">
                    <div class="pure-u-1-2">
                        {customCompanyDropdownRender}
                    </div>
                    <div class="pure-u-1-2">
                        {customTeamDropdownRender}
                    </div>                    
                    
                </div>           
                <div class="pure-g">
                    <div class="pure-u-1-1">
                        &nbsp;
                    </div>
   
                </div>
            </div>
            <div class="pure-g">
                <div class="pure-u-1-4">
                    <TRManagerCard {...EnquiryCardProps} ></TRManagerCard>
                </div>
                <div class="pure-u-1-4">
                    <TRManagerCard {...BookingCardProps}></TRManagerCard>
                </div>
                <div class="pure-u-1-4">
                    <TRManagerCard {...FollowupCardProps}></TRManagerCard>
                </div>
                <div class="pure-u-1-4">    
                    <TRManagerCard {...SalesCardProps}></TRManagerCard>
                </div>
            </div>
            <div class="pure-g">
                <div class="pure-u-1-1">
                    {/* {customDropdownRender} */}
                </div>
                {/* <div class="pure-u-1-2">
                    <TRCharjsBar ></TRCharjsBar>
                    
                </div>
                <div class="pure-u-1-2">
                   <TRChartjsLine></TRChartjsLine>
                </div> */}
            </div>
            <div class="pure-u-1-1">
                <TRTable></TRTable>
            </div>
            <div class="pure-u-1-1">
                {/* <TRChartDoughnut></TRChartDoughnut>
                <TRChartPie></TRChartPie> */}
            </div>
        </div>
    );
  }
}

export default DMSDashboard;
