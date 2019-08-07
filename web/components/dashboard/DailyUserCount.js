import React, { Component } from 'react';

import MUIDataTable from "mui-datatables";
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles';
import { getUserCount } from '../../lib/api/dashboard';
import { connect } from 'react-redux';
import { showDailyLeads } from '../../lib/store';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import IconButton from '@material-ui/core/IconButton';

const columns = [
    {
        name: "Date",
        options: {
            filter: true,
            sortDirection: 'desc'
        }
    },
    {
        name: "Count",
        options: {
            filter: false,
        }
    },
];

class DailyUserCount extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userLeads: [],
            filter: true,
            search: true,
            print: false,
            download: false,
            selectableRows: false,
            selectableRowsOnClick: false,
            filterType: 'dropdown',
            responsive: 'stacked',
            rowsPerPage: 10,
        };
        this.backButtonClick = this.backButtonClick.bind(this);
    }

    backButtonClick() {
        console.log("Inside backButtonnnnnnnnnnnnnn");
        this.props.showDailyLeads(false);
    }

    async componentDidUpdate(prevProps) {
        console.log("the componentDidUpdate props are ", this.props.dailyLeadsUser, this.props.showDailyLeads);
        let filters = this.props.filters;
        let old_filters = prevProps.filters;
        if (this.props.dailyLeadsUser && (this.props.dailyLeadsUser != prevProps.dailyLeadsUser|| filters.month != old_filters.month || filters.year != old_filters.year)){
            console.log("changing state ----");
            this.setState({ userLeads: await this.getDailyUserCount() });
        }
    }

    async getDailyUserCount() {
        console.log("Inside  getUserLeads");
        try {
            if (!this.props.dailyLeadsUser || ! this.props.filters) return [];
            let filters = this.props.filters;
            const data = await getUserCount(filters.team[1],this.props.dailyLeadsUser[0],filters.month, filters.year);
            console.log("The result is ", data);
            if (data == null) {
                return [];
            }
            let result = data.map(userLead => {
                return [userLead.date, userLead.count];
            })
            return result;
        } catch (err) {
            console.log(err); // eslint-disable-line
            return [];
        }
    }
    async componentDidMount() {
        this.setState({ userLeads: await this.getDailyUserCount() });
    }

    getMuiTheme = () => createMuiTheme({
        overrides: {
            MUIDataTable: {
                root: {
                    backgroundColor: "#FFFFCC",
                },
                paper: {
                    boxShadow: "none",
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
        let userLeads = this.state.userLeads;
        console.log("Calling table render", userLeads);
        return (
            <MuiThemeProvider theme={this.getMuiTheme()}>
                <IconButton aria-label="delete" size="small">
                    <ArrowBackIcon fontSize="inherit" onClick={this.backButtonClick} />
                </IconButton>
                <MUIDataTable
                    title={this.props.dailyLeadsUser[1]}
                    data={userLeads}
                    columns={["date", "count"]}
                    options={this.state}
                />
            </MuiThemeProvider>

        );
    }

}
const mapStateToProps = state => {
    console.log("state in mapping", state);
    return { filters: state.performanceFilters,dailyLeadsUser: state.dailyleads_userIndex };
}
const mapDispatchToProps = { showDailyLeads };

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(DailyUserCount);