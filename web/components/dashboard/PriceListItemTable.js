import React from "react";
import {
    CircularProgress,
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
        name: "product_id",
        options: {
            filter: true,
            sortDirection: 'asc'
        }
    },
    {
        name: "product_name",
        options: {
            filter: true,
            sortDirection: 'asc'
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
    }
];

class PriceListItemTable extends React.Component {

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
        console.log("Inside priceListFileItems method", priceListData);
        let result = null;
        try {
            if (priceListData === null && !(Array.isArray(priceListData.record))) {
                return [];
            } else {
                result = priceListData.records.map(record => {
                    return [record.product_id[0], record.product_id[1], record.date_start, record.date_end, record.fixed_price];
                })
            }
            return result;
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
)(withStyles(styles)(PriceListItemTable));