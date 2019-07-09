import React from "react";
import {
    Grid,
    CircularProgress,
    Typography,
    withStyles,
    Button,
    Tabs,
    Tab,
    TextField,
    Fade
} from "@material-ui/core";
import classnames from "classnames";
import { getLoginCreds } from '../lib/api/admin';
import Router from 'next/router';
import { login } from '../lib/store';
import { connect } from 'react-redux';

class Login extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            mobile: '',
            pin: '',
            error:''
        };
        this.handleMobileChange = this.handleMobileChange.bind(this);
        this.handlePinChange = this.handlePinChange.bind(this);
    }

    static async getInitialProps(ctx) {
        console.log("Hello from initi props - user login");
        const props = {};
        props.activeTabId = 0;
        props.loginValue = "";
        props.passwordValue = "";
        props.reduxStore = ctx.reduxStore;
        console.log("The redux store ",ctx.reduxStore);
        return props;
    }

    onSubmitHandler = (e) => {
        console.log("Inside onSubmitHandler");
        e.preventDefault();
        this.checkForLoginUser();
    }

    handleMobileChange(evt) {
        console.log("Inside handleMobileChange", evt.target.value);
        this.setState({ mobile: evt.target.value });
    }

    handlePinChange(evt) {
        console.log("Inside handlePinChange", evt.target.value);
        this.setState({ password: evt.target.value });
    }

    async checkForLoginUser() {
        console.log("Inside checkForLoginUser");
        try {
            console.log("State in checklogin",this.state);
            const data = await getLoginCreds(this.state);
            console.log("The result is ",data.error);
            if(data.error === undefined || data.error === null || data.error === ""){
                console.log("Correct input");
                console.log("Data",data);
                console.log("Props in user login",this.props);
                this.props.login(data);
                Router.push('/');
            }else{
                console.log("Wrong input",this.props.errorValue);
                
                this.setState({error : "Email/Password incorrect" });
            }
        } catch (err) {
            console.log(err); // eslint-disable-line
        }
    }
    
    render() {
        const { classes } = this.props;
        const props = this.props;
        console.log("The props classes are ", props);
        return (
            <Grid container className={classes.container}>
                <div className={classes.logotypeContainer}>
                    <img src="/static/crick1.png" alt="logo" className={classes.logotypeImage} /> 
                    <Typography className={classes.logotypeText}>BOOK A SERVICE</Typography>
                </div>
                <div className={classes.formContainer}>
                    <div className={classes.form}>
                        <Tabs
                            value={props.activeTabId}
                            onChange={props.handleTabChange}
                            indicatorColor="primary"
                            textColor="primary"
                            centered
                        >
                            <Tab label="Login" classes={{ root: classes.tab }} />
                            <Tab label="New User" classes={{ root: classes.tab }} />
                        </Tabs>
                        {props.activeTabId === 0 && (
                            <React.Fragment>
                                <Typography variant="h1" className={classes.greeting}>
                                    Good Morning, User
            </Typography>
                                <Button size="large" className={classes.googleButton}>
                                    <img src="./static/google.svg" alt="google" className={classes.googleIcon} /> 
                                    &nbsp;Sign in with Google
            </Button>
                                <div className={classes.formDividerContainer}>
                                    <div className={classes.formDivider} />
                                    <Typography className={classes.formDividerWord}>or</Typography>
                                    <div className={classes.formDivider} />
                                </div>
                                <Fade in={props.error}>
                                    <Typography color="secondary" className={classes.errorMessage}>
                                        Something is wrong with your login or password :(
              </Typography>
                                </Fade>
                                <TextField
                                    id="mobile"
                                    InputProps={{
                                        classes: {
                                            underline: classes.textFieldUnderline,
                                            input: classes.textField
                                        }
                                    }}
                                    //value={props.loginValue}
                                    onChange={this.handleMobileChange}
                                    margin="normal"
                                    placeholder="Mobile Number"
                                    type="number"
                                    fullWidth
                                />
                                <TextField
                                    id="password"
                                    InputProps={{
                                        classes: {
                                            underline: classes.textFieldUnderline,
                                            input: classes.textField
                                        }
                                    }}
                                    //value={props.passwordValue}
                                    onChange={this.handlePinChange}
                                    margin="normal"
                                    placeholder="Password"
                                    type="password"
                                    fullWidth
                                />
                                <div className={classes.errorMessage}>{this.state.error}</div>
                                <div className={classes.formButtons}>
                                    {props.isLoading ? (
                                        <CircularProgress size={26} className={classes.loginLoader} />
                                    ) : (
                                            <Button
                                                /* disabled={
                                                    props.loginValue.length === 0 ||
                                                    props.passwordValue.length === 0
                                                } */
                                                onClick={this.onSubmitHandler}
                                                variant="contained"
                                                color="primary"
                                                size="large"
                                            >
                                                Login
                </Button>
                                        )}
                                    <Button
                                        color="primary"
                                        size="large"
                                        className={classes.forgetButton}
                                    >
                                        Forget Password
              </Button>
                                </div>
                            </React.Fragment>
                        )}
                        {props.activeTabId === 1 && (
                            <React.Fragment>
                                <Typography variant="h1" className={classes.greeting}>
                                    Welcome!
            </Typography>
                                <Typography variant="h2" className={classes.subGreeting}>
                                    Create your account
            </Typography>
                                <Fade in={props.error}>
                                    <Typography color="secondary" className={classes.errorMessage}>
                                        Something is wrong with your login or password :(
              </Typography>
                                </Fade>
                                <TextField
                                    id="name"
                                    InputProps={{
                                        classes: {
                                            underline: classes.textFieldUnderline,
                                            input: classes.textField
                                        }
                                    }}
                                    value={props.nameValue}
                                    onChange={e => props.handleInput(e, "name")}
                                    margin="normal"
                                    placeholder="Full Name"
                                    type="email"
                                    fullWidth
                                />
                                <TextField
                                    id="email"
                                    InputProps={{
                                        classes: {
                                            underline: classes.textFieldUnderline,
                                            input: classes.textField
                                        }
                                    }}
                                    value={props.loginValue}
                                    onChange={e => props.handleInput(e, "login")}
                                    margin="normal"
                                    placeholder="Email Adress"
                                    type="email"
                                    fullWidth
                                />
                                <TextField
                                    id="password"
                                    InputProps={{
                                        classes: {
                                            underline: classes.textFieldUnderline,
                                            input: classes.textField
                                        }
                                    }}
                                    value={props.passwordValue}
                                    onChange={e => props.handleInput(e, "password")}
                                    margin="normal"
                                    placeholder="Password"
                                    type="password"
                                    fullWidth
                                />
                                <div className={classes.errorMessage}>{props.errorValue}</div>
                                <div className={classes.creatingButtonContainer}>
                                    {props.isLoading ? (
                                        <CircularProgress size={26} />
                                    ) : (
                                            <Button
                                                onClick={props.handleLoginButtonClick}
                                                disabled={
                                                    props.loginValue.length === 0 ||
                                                    props.passwordValue.length === 0 ||
                                                    props.nameValue.length === 0
                                                }
                                                size="large"
                                                variant="contained"
                                                color="primary"
                                                fullWidth
                                                className={classes.createAccountButton}
                                            >
                                                Create your account
                </Button>
                                        )}
                                </div>
                                <div className={classes.formDividerContainer}>
                                    <div className={classes.formDivider} />
                                    <Typography className={classes.formDividerWord}>or</Typography>
                                    <div className={classes.formDivider} />
                                </div>
                                <Button
                                    size="large"
                                    className={classnames(
                                        classes.googleButton,
                                        classes.googleButtonCreating
                                    )}
                                >
                                    {/* <img src={google} alt="google" className={classes.googleIcon} /> */}
                                    &nbsp;Sign in with Google
            </Button>
                            </React.Fragment>
                        )}
                    </div>
                    <Typography color="primary" className={classes.copyright}>
                        © Copyright  2018-2019 , TurnRight Private Ltd.
      </Typography>
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
        left: 0
    },
    logotypeContainer: {
        backgroundColor: theme.palette.primary.main,
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
    logotypeImage: {
        width: 165,
        marginBottom: theme.spacing.unit * 4
    },
    logotypeText: {
        color: "white",
        fontWeight: 500,
        fontSize: 84,
        [theme.breakpoints.down("md")]: {
            fontSize: 48
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
        width: 320
    },
    tab: {
        fontWeight: 400,
        fontSize: 18
    },
    greeting: {
        fontWeight: 500,
        textAlign: "center",
        marginTop: theme.spacing.unit * 4,
        fontSize: 50
    },
    subGreeting: {
        fontWeight: 500,
        textAlign: "center",
        marginTop: theme.spacing.unit * 2
    },
    googleButton: {
        marginTop: theme.spacing.unit * 6,
        //boxShadow: theme.customShadows.widget,
        backgroundColor: "white",
        width: "100%",
        textTransform: "none"
    },
    googleButtonCreating: {
        marginTop: 0
    },
    googleIcon: {
        width: 30,
        marginRight: theme.spacing.unit * 2
    },
    creatingButtonContainer: {
        marginTop: theme.spacing.unit * 2.5,
        height: 46,
        display: "flex",
        justifyContent: "center",
        alignItems: "center"
    },
    createAccountButton: {
        height: 46,
        textTransform: "none"
    },
    formDividerContainer: {
        marginTop: theme.spacing.unit * 4,
        marginBottom: theme.spacing.unit * 4,
        display: "flex",
        alignItems: "center"
    },
    formDividerWord: {
        paddingLeft: theme.spacing.unit * 2,
        paddingRight: theme.spacing.unit * 2
    },
    formDivider: {
        flexGrow: 1,
        height: 1,
        backgroundColor: theme.palette.text.hint + "40"
    },
    errorMessage: {
        textAlign: "center",
        color: "red",
        fontWeight: 500,
        background: "#bbec40"
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
        }
    },
    textField: {
        borderBottomColor: theme.palette.background.light
    },
    formButtons: {
        width: "100%",
        marginTop: theme.spacing.unit * 4,
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center"
    },
    forgetButton: {
        textTransform: "none",
        fontWeight: 400
    },
    loginLoader: {
        marginLeft: theme.spacing.unit * 4
    },
    copyright: {
        marginTop: theme.spacing.unit * 4,
        whiteSpace: 'nowrap',
        [theme.breakpoints.up("md")]: {
            position: "absolute",
            bottom: theme.spacing.unit * 2,
        }
    }
});
const mapStateToProps = state =>  {
    //const {app_state} = state;     
    console.log("state in mapping",state);
    return {user:state.user};
}
const mapDispatchToProps = { login }
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withStyles(styles, { withTheme: true })(Login));
