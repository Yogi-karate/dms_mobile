import React from "react";
import TRManagerCard from './TRManagerCard';
import TRTable from './TRTable';
import TRCustomDropDown from '../common/Dropdown';
import { getInventoryStock } from '../../lib/api/dashboard';


class InventoryCardComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            inventories: [],
            cardTitle: 'Inventory',
            cardColor: 'success',
            cardIcon: 'faWarehouse',
            graphType: 'pie',
            graphColors: [
                '#48DA4A',
                '#3BD574',
                '#28B85D'
            ],
            renderDropdown: false,
        };
    }

    async getResultData() {
        try {
            console.log("The complete props ", this.props)
            const resp = await getInventoryStock();
            console.log("The data from admin js for inventory", resp);
            let graphData = [];
            let graphLabel = [];
            const inventoryArray = resp.result.map((inventory) => {
                console.log("The inventory are ", inventory);
                graphData.push(inventory.state_count);
                graphLabel.push(inventory.state + " - " + inventory.state_count);
                return [inventory.state, inventory.state_count]
            });
            console.log("The data inventory", inventoryArray);
            return ({ inventories: inventoryArray, graphData: graphData, graphLabel: graphLabel }); // eslint-disable-line
        } catch (err) {
            console.log(err); // eslint-disable-line
        }
    }

    async componentDidMount() {
        this.setState(await this.getResultData());
    }

    render() {
        return (
            <div>

                <TRManagerCard {...this.state} ></TRManagerCard>
            </div>
        );
    }
}

export default InventoryCardComponent;
