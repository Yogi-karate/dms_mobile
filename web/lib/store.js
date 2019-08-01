import { createStore, applyMiddleware } from 'redux'
import { composeWithDevTools } from 'redux-devtools-extension'

const exampleInitialState = {
  lastUpdate: 0,
  light: false,
  count: 0,
  user: null,
  team: null,
  lead_state: null,
  dailyleads_userIndex: null,
  showDailyLeads: null,
}

export const actionTypes = {
  TICK: 'TICK',
  INCREMENT: 'INCREMENT',
  DECREMENT: 'DECREMENT',
  RESET: 'RESET',
  LOGIN: 'LOGIN',
  TEAM_CHANGE: 'TEAM',
  LEAD_STATE: 'LEAD_STATE',
  DAILYLEADS_USERINDEX: 'DAILYLEADS_USERINDEX',
  SHOWDAILYLEADS: 'SHOWDAILYLEADS'
}

// REDUCERS
export const reducer = (state = exampleInitialState, action) => {
  switch (action.type) {
    case actionTypes.TICK:
      return Object.assign({}, state, {
        lastUpdate: action.ts,
        light: !!action.light
      })
    case actionTypes.INCREMENT:
      return Object.assign({}, state, {
        count: state.count + 1
      })
    case actionTypes.DECREMENT:
      return Object.assign({}, state, {
        count: state.count - 1
      })
    case actionTypes.RESET:
      return Object.assign({}, state, {
        count: exampleInitialState.count
      })
    case actionTypes.LOGIN:
      return Object.assign({}, state, {
        user: action.user
      })
    case actionTypes.TEAM_CHANGE:
      return Object.assign({}, state, {
        team: action.team
      })
    case actionTypes.LEAD_STATE:
      return Object.assign({}, state, {
        lead_state: action.leadstate
      })
    case actionTypes.DAILYLEADS_USERINDEX:
      return Object.assign({}, state, {
        dailyleads_userIndex: action.dailyLead
      })
    case actionTypes.SHOWDAILYLEADS:
      return Object.assign({}, state, {
        showDailyLeads: action.showDailyLeads
      })

    default:
      return state
  }
}

// ACTIONS
export const login = (user) => {
  console.log("Trying store login");
  return { type: actionTypes.LOGIN, user: user }
}
export const team = (team) => {
  console.log("Trying to change team");
  return { type: actionTypes.TEAM_CHANGE, team: team }
}
export const state_select = (leadstate) => {
  console.log("Trying to change enq stage");
  return { type: actionTypes.LEAD_STATE, leadstate: leadstate }
}
export const dailyleads_userIndex = (dailyLead) => {
  console.log("Trying to store daily lead row data");
  return { type: actionTypes.DAILYLEADS_USERINDEX, dailyLead: dailyLead }
}

export const showDailyLeads = (showDailyLeads) => {
  console.log("Trying to show daily leads");
  return { type: actionTypes.SHOWDAILYLEADS, showDailyLeads: showDailyLeads }
}
export const serverRenderClock = () => {
  return { type: actionTypes.TICK, light: false, ts: Date.now() }
}
export const startClock = () => {
  return { type: actionTypes.TICK, light: true, ts: Date.now() }
}

export const incrementCount = () => {
  return { type: actionTypes.INCREMENT }
}

export const decrementCount = () => {
  return { type: actionTypes.DECREMENT }
}

export const resetCount = () => {
  return { type: actionTypes.RESET }
}

export function initializeStore(initialState = exampleInitialState) {
  return createStore(
    reducer,
    initialState,
    composeWithDevTools(applyMiddleware())
  )
}
