import React from "react";
import {
    Grid,
    CircularProgress,
    Typography,
    Button,
    Tabs,
    Tab,
    TextField,
    Fade,
    MenuItem
} from "@material-ui/core";
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import { withStyles } from '@material-ui/styles';
import MUIDataTable from "mui-datatables";

const options = {
    filter: true,
    search: true,
    print: false,
    download: false,
    selectableRows: 'none',
    filterType: 'dropdown',
    responsive: 'stacked',
    rowsPerPage: 10,
};

const columns = [
    {
        name: "item_id",
        options: {
            filter: true,
            sortDirection: 'asc'
        }
    },
    {
        name: "applied_on",
        options: {
            filter: true,
            sortDirection: 'asc'
        }
    },
    {
        name: "base",
        options: {
            filter: false,
        }
    },
    {
        name: "compute_price",
        options: {
            filter: false,
        }
    },
    {
        name: "date_end",
        options: {
            filter: false,
        }
    },
    {
        name: "date_start",
        options: {
            filter: true,
            sortDirection: 'asc'
        }
    },
    {
        name: "fixed_price",
        options: {
            filter: false,
        }
    },
    {
        name: "min_quantity",
        options: {
            filter: false,
        }
    },
    {
        name: "percent_price",
        options: {
            filter: false,
        }
    },
    {
        name: "price_discount",
        options: {
            filter: true,
            sortDirection: 'asc'
        }
    },
    {
        name: "pricelist_id",
        options: {
            filter: false,
        }
    },
    {
        name: "product_id",
        options: {
            filter: false,
        }
    }
];

class PriceListFileDetailsTable extends React.Component {

    constructor(props) {
        super(props);
        this.props = props;
        console.log("dropdown props are", this.props);
        this.state = {
            priceListFileItems: [],
            isLoading: true
        };
    }

     async componentDidUpdate(prevProps) {
        console.log("Old props and new props in priceList item table", this.props.priceListFileItems, prevProps.priceListFileItems);
        if (this.props.priceListFileItems != prevProps.priceListFileItems) {
            console.log("changing state priceList item table----");
            this.setState({ isLoading: true });
            this.setState({ priceListFileItems: await this.priceListFileItems(this.props.priceListFileItems) });
            this.setState({ isLoading: false });
        }
    }    

     async componentDidMount() {
        try {
            console.log("Inside componentDidMount pricelist file details", this.props.priceListFileItems);
            this.setState({ priceListFileItems: await this.priceListFileItems(this.props.priceListFileItems) });
            this.setState({ isLoading: false });
        } catch (err) {
            console.log(err); // eslint-disable-line
        }
    } 

    async priceListFileItems(priceListData) {
        console.log("Inside priceListFileItems method");
        try {
            let itemsArray = null;
            let finalItems = [];
            if (priceListData != null) {
                itemsArray = priceListData.map(record => {
                    if (Array.isArray(record.values)) {
                        return record.values.map(item => {
                            return [item.item_id, item.item.applied_on, item.item.base, item.item.compute_price, item.item.date_end, item.item.date_start, item.item.fixed_price, item.item.min_quantity, item.item.percent_price, item.item.price_discount, item.item.pricelist_id, item.item.product_id];
                        })
                    };
                });
                console.log("The itemsArray for priceListFileItems ", itemsArray);
                itemsArray = itemsArray.filter((element) => {
                    return element !== undefined;
                });
                console.log("The itemsArray after filtering is ", itemsArray);
                for (let i = 0; i < itemsArray.length; i++) {
                    for (let j = 0; j < itemsArray[i].length; j++) {
                        finalItems.push(itemsArray[i][j]);
                    }
                }
                console.log("The returning final itemsArray is ", finalItems);
                return finalItems;
            } else {
                return [];
            }
        } catch (err) {
            console.log(err); // eslint-disable-line
            return [];
        }
    }


    getMuiTheme = () => createMuiTheme({
        overrides: {
            MUIDataTable: {
                root: {
                    backgroundColor: "#FFFFCC",
                },
                paper: {
                    boxShadow: "none",
                    marginTop: "70px",
                    borderTop: "1px solid #000000"
                }
            },
            MUIDataTableBodyCell: {
                fixedHeader: {
                    background: '#D2F7FC'
                }
            },
            MUIDataTableHeadCell: {
                root: {
                    backgroundColor: "#D2F7FC"
                }
            },
            MUIDataTableSelectCell: {
                headerCell: {
                    background: '#3B9AF0'
                }
            }
        }
    });

    render() {
        const { classes } = this.props;
        const props = this.props;
        let priceListFileItems = this.state.priceListFileItems;
        let header = "PriceList Items";
        return (
            <MuiThemeProvider theme={this.getMuiTheme()}>
                {this.state.isLoading ? (
                    <CircularProgress size={100} className={classes.loginLoader} />
                ) : (
                        <MUIDataTable
                            title={header}
                            data={priceListFileItems}
                            columns={columns}
                            options={options}
                        />
                    )}
            </MuiThemeProvider>
        )
    }
};

const styles = theme => ({
    loginLoader: {
        marginLeft: theme.spacing(80),
    }
});

const mapStateToProps = state => {
    console.log("redux fileState form details in mapping", state);
    return { priceListFileItems: state.priceListFileItems };
}

export default connect(
    mapStateToProps,
    null
)(withStyles(styles)(PriceListFileDetailsTable));