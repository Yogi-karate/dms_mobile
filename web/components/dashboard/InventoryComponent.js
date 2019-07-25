import React from "react";
import TRManagerCard from './TRManagerCard';
import TRTable from './TRTable';
import TRCustomDropDown from '../common/Dropdown';
import { getSalesDashboard } from '../../lib/api/admin';


class InventoryCardComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            inventories: [],
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
        };
    }

    async componentDidMount() {
        try {
            console.log("The complete props ", this.props)
            const resp = await getSalesDashboard();
            console.log("The data from admin js for inventory", resp);
            let graphData = [];
            let graphLabel = [];
            const inventoryArray = resp[0].result.map((inventory) => {
                console.log("The inventory are ", inventory);
                graphData.push(inventory.state_count);
                graphLabel.push(inventory.state);
                return [inventory.state, inventory.state_count]
            });
            console.log("The data inventory", inventoryArray);
            this.setState({ inventories: inventoryArray, graphData: graphData, graphLabel: graphLabel }); // eslint-disable-line

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

export default InventoryCardComponent;
