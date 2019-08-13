import React from "react";
import TRManagerCard from './TRManagerCard';
import TRTable from './TRTable';
import TRCustomDropDown from '../common/Dropdown';
import { getSalesDashboard } from '../../lib/api/dashboard';


class SalesCardComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            sales: [],
            cardTitle: 'Bookings',
            cardColor: 'info',
            cardIcon: 'faCar',
            graphType: 'pie',
            graphColors: [
                '#AFEEEE',
                '#48D1CC',
                '#00CED1',
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
                //console.log("The sales are ",sales);
                switch (sales.state) {
                    case "draft":
                        graphData.push(sales.state_count);
                        graphLabel.push("Quotations" + " - " + sales.state_count);
                        break;
                    case "sale":
                        graphData.push(sales.state_count);
                        graphLabel.push("Sale Orders" + " - " + sales.state_count);
                        break;
                    case "cancel":
                        graphData.push(sales.state_count);
                        graphLabel.push("cancel" + " - " + sales.state_count);
                        break;
                    default:
                        break;
                }
                //graphLabel.push(sales.state + " - " + sales.state_count);
                return [sales.state, sales.state_count]
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
                <TRManagerCard {...this.state} ></TRManagerCard>
            </div>
        );
    }
}

export default SalesCardComponent;
