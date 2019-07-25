import React from "react";
import TRManagerCard from './TRManagerCard';
import TRTable from './TRTable';
import TRCustomDropDown from '../common/Dropdown';
import { getSalesDashboard } from '../../lib/api/admin';


class SalesCardComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            sales: [],
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
        };
    }

    async componentDidMount() {
        try {
            console.log("The complete props ", this.props)
            const resp = await getSalesDashboard();
            console.log("The data from admin js for sales", resp);
            let graphData = [];
            let graphLabel = [];
            const salesArray = resp[0].result.map((sales) => {
                console.log("The sales are ",sales);
                graphData.push(sales.state_count);
                graphLabel.push(sales.state);
                return[sales.state, sales.state_count]
            });
            console.log("The data sales", salesArray);
            this.setState({ sales: salesArray, graphData: graphData, graphLabel: graphLabel }); // eslint-disable-line

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

export default SalesCardComponent;
