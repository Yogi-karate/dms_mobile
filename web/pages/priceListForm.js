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
import classnames from "classnames";
import { connect } from 'react-redux';
import { withStyles } from '@material-ui/styles';
import withLayout from '../lib/withLayout';
import { getCompanies } from '../lib/api/dashboard';
import { priceListUpload } from '../lib/api/dashboard';
import { priceListItems } from '../lib/api/dashboard';
import { noAuto } from "@fortawesome/fontawesome-svg-core";
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
        name: "ID",
        options: {
            filter: true,
            sortDirection: 'asc'
        }
    },
    {
        label: "Name",
        name: "Name",
        options: {
            filter: true,
            sortDirection: 'asc'
        }
    },
    {
        name: "ProductID",
        options: {
            filter: false,
        }
    },
    {
        name: "ProductName",
        options: {
            filter: false,
        }
    },
    {
        name: "FixedPrice",
        options: {
            filter: false,
        }
    }
];

class PriceListForm extends React.Component {

    constructor(props) {
        super(props);
        this.props = props;
        console.log("dropdown props are", this.props);
        this.state = {
            name: '',
            companies: [],
            file: null,
            company: '',
            priceListItems: [],
            fileName: ''
        };
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleCompanyChange = this.handleCompanyChange.bind(this);
        this.handleFileChange = this.handleFileChange.bind(this);
    }

    static async getInitialProps(ctx) {
        const props = {};
        props.activeTabId = 0;
        console.log("The getInitialProps are ", props);
        return props;
    }

    onSubmitHandler = (e) => {
        console.log("Inside onSubmitHandler");
        e.preventDefault();
        this.submitPriceListForm();
    }

    handleNameChange(evt) {
        this.setState({ name: evt.target.value });
    }

    handleCompanyChange = prop => event => {
        this.setState({ [prop]: event.target.value });
        this.setState({ company: event.target.value });
        console.log("The selected val is ", event.target.value);
        //this.props.company(event.target.value);
    };

    handleFileChange(evt) {
        console.log("The handleFileChange ", evt.target.files[0]);
        this.setState({ file: evt.target.files[0] });
        if (evt.target.files[0].name != undefined || evt.target.files[0].name != null) {
            this.setState({ fileName: evt.target.files[0].name });
        } else {
            this.setState({ fileName: 'empty' });
        }
    }

    async componentDidMount() {
        console.log("Inside getting companiesss");
        try {
            console.log("Printing the user ", this.props.user);
            const data = await getCompanies();
            console.log("The result is ", data);
            console.log("The result companies is ", data.records);
            if (data == null || data == [] || data.records == []) {
                this.setState({ companies: [], company: "" });
            } else {
                let result = data.records.map(company => {
                    return [company.name, company.id];
                })
                console.log("The result companies is ", result[0][1]);
                this.setState({ companies: result, company: result[0] });
                //this.props.company(result[0]);
            }
        } catch (err) {
            console.log(err); // eslint-disable-line
        }

    }

    async submitPriceListForm() {
        try {
            const formData = new FormData();
            formData.append('file', this.state.file);
            formData.append("name", this.state.name);
            formData.append("company", this.state.company[1]);
            console.log("The formdata is ", formData);
            const data = await priceListUpload(formData);
            console.log("The result is ", data);
            console.log("The arguments for priceListItems are ", this.state.name);
            this.setState({ priceListItems: await this.getPriceListItems(this.state.name) });
        } catch (err) {
            console.log(err); // eslint-disable-line
        }
    }

    async getPriceListItems(name) {
        console.log("Inside getStateData", this.props.stage);
        try {
            let result = null;
            const data = await priceListItems(name);
            console.log("the priceList items are ", data);
            if (data == null) {
                return [];
            } else {
                result = data.map(record => {
                    return [record.pricelist_id[0], record.pricelist_id[1], record.product_id[0], record.product_id[1], record.fixed_price];
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
                    width: "190%",
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
        let priceListItems = this.state.priceListItems;
        let header = "PriceList Item";
        return (
            <Grid container className={classes.container}>
                <div className={classes.formContainer}>
                    <div className={classes.form}>
                        <Tabs
                            value={props.activeTabId}
                            onChange={props.handleTabChange}
                            indicatorColor="white"
                            textColor="white"
                            centered
                        >
                            <Tab label="PRICELIST FORM" className={classes.formTab} />
                        </Tabs>
                        {props.activeTabId === 0 && (
                            <React.Fragment>
                                <TextField
                                    id="name"
                                    InputProps={{
                                        classes: {
                                            underline: classes.textFieldUnderline,
                                            input: classes.textField
                                        }
                                    }}
                                    defaultValue={this.state.name}
                                    onChange={this.handleNameChange}
                                    margin="normal"
                                    placeholder="Enter the file name"
                                    type="string"
                                    fullWidth
                                />
                                <TextField
                                    select
                                    className={classes.textField}
                                    label="Select a Company"
                                    value={this.state.company}
                                    onChange={this.handleCompanyChange('company')}
                                    fullWidth
                                >
                                    {this.state.companies.map(option => (
                                        <MenuItem key={option[1]} value={option}>
                                            {option[0]}
                                        </MenuItem>
                                    ))}
                                </TextField>
                                <input
                                    accept="*"
                                    className={classes.uploadInput}
                                    id="raised-button-file"
                                    multiple
                                    type="file"
                                    onChange={this.handleFileChange}
                                />
                                <label htmlFor="raised-button-file">
                                    <Button variant="raised" component="span" className={classes.uploadButton}
                                        variant="contained"
                                        size="small"
                                    >
                                        Upload
                                    </Button>
                                </label>
                                <span className={classes.uploadedFileName}>{this.state.fileName}</span>
                                <div className={classes.formButtons}>
                                    {this.state.isLoading ? (
                                        <CircularProgress size={26} className={classes.loginLoader} />
                                    ) : (
                                            <Button
                                                disabled={
                                                    this.state.companies.length === 0 ||
                                                    this.state.name.length === 0 ||
                                                    this.state.fileName.length === 0
                                                }
                                                onClick={this.onSubmitHandler}
                                                variant="contained"
                                                color="primary"
                                                size="large"
                                            >
                                                SUBMIT
                                        </Button>
                                        )}
                                </div>
                            </React.Fragment>
                        )}
                    </div>

                    <MuiThemeProvider theme={this.getMuiTheme()}>
                        <MUIDataTable
                            title={header}
                            data={priceListItems}
                            columns={columns}
                            options={options}
                        />
                    </MuiThemeProvider>

                    {/* <Typography color="primary" className={classes.copyright}>
                        © Copyright  2018-2019 , TurnRight Private Ltd.
                    </Typography> */}
                </div>
            </Grid>
        )
    }
};

const styles = theme => ({
    container: {
        height: "100vh",
        width: "100vw",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        position: "absolute",
        top: 0,
        left: 0,
        background: "#ffffff"
    },
    logotypeContainer: {
        backgroundColor: "#212121",
        width: "60%",
        height: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        [theme.breakpoints.down("md")]: {
            width: "50%"
        },
        [theme.breakpoints.down("md")]: {
            display: "none"
        }
    },
    formContainer: {
        width: "40%",
        height: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        [theme.breakpoints.down("md")]: {
            width: "50%"
        }
    },
    form: {
        width: 500
    },
    formTab: {
        fontWeight: 400,
        fontSize: 48,
        background: "linear-gradient(45deg, #0066cc 30%, #0099ff 90%)",
        borderradius: "3px",
        border: 0,
        color: "white",
        height: "88px",
        padding: "0 30px",
        boxshadow: "0 3px 5px 2px rgba(255, 105, 135, 0.3)",
        minWidth: 900
    },
    errorMessage: {
        textAlign: "center",
        color: "red",
        fontWeight: 500,
        background: "#f5f5f5"
    },
    textFieldUnderline: {
        "&:before": {
            borderBottomColor: theme.palette.primary.light
        },
        "&:after": {
            borderBottomColor: theme.palette.primary.main
        },
        "&:hover:before": {
            borderBottomColor: `${theme.palette.primary.light} !important`
        },
        marginTop: "30px",
        marginBottom: "30px"
    },
    textField: {
        borderBottomColor: theme.palette.background.light,
        marginBottom: "10px"
    },
    formButtons: {
        width: "100%",
        marginTop: theme.spacing(4),
        marginLeft: theme.spacing(25),
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
    },
    loginLoader: {
        marginLeft: theme.spacing(4),
    },
    copyright: {
        marginTop: theme.spacing(4),
        whiteSpace: 'nowrap',
        [theme.breakpoints.up("md")]: {
            position: "absolute",
            bottom: theme.spacing(2),
        }
    },
    uploadInput: {
        display: "none",
    },
    uploadButton: {
        marginTop: "27px",
        color: "green",
        background: "#ffffff"
    },
    uploadedFileName: {
        position: "relative",
        top: "18px",
        left: "15px",
        color: "#3b46d1"
    }
});
const mapStateToProps = state => {
    //const {app_state} = state;     
    console.log("state in mapping", state);
    return { user: state.user };
}

export default connect(
    mapStateToProps,
    null
)(withStyles(styles)(PriceListForm));