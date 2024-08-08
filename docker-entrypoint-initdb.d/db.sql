--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3 (Debian 16.3-1.pgdg120+1)
-- Dumped by pg_dump version 16.3 (Debian 16.3-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admin_event_entity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.admin_event_entity (
    id character varying(36) NOT NULL,
    admin_event_time bigint,
    realm_id character varying(255),
    operation_type character varying(255),
    auth_realm_id character varying(255),
    auth_client_id character varying(255),
    auth_user_id character varying(255),
    ip_address character varying(255),
    resource_path character varying(2550),
    representation text,
    error character varying(255),
    resource_type character varying(64)
);


ALTER TABLE public.admin_event_entity OWNER TO admin;

--
-- Name: associated_policy; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.associated_policy (
    policy_id character varying(36) NOT NULL,
    associated_policy_id character varying(36) NOT NULL
);


ALTER TABLE public.associated_policy OWNER TO admin;

--
-- Name: authentication_execution; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.authentication_execution (
    id character varying(36) NOT NULL,
    alias character varying(255),
    authenticator character varying(36),
    realm_id character varying(36),
    flow_id character varying(36),
    requirement integer,
    priority integer,
    authenticator_flow boolean DEFAULT false NOT NULL,
    auth_flow_id character varying(36),
    auth_config character varying(36)
);


ALTER TABLE public.authentication_execution OWNER TO admin;

--
-- Name: authentication_flow; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.authentication_flow (
    id character varying(36) NOT NULL,
    alias character varying(255),
    description character varying(255),
    realm_id character varying(36),
    provider_id character varying(36) DEFAULT 'basic-flow'::character varying NOT NULL,
    top_level boolean DEFAULT false NOT NULL,
    built_in boolean DEFAULT false NOT NULL
);


ALTER TABLE public.authentication_flow OWNER TO admin;

--
-- Name: authenticator_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.authenticator_config (
    id character varying(36) NOT NULL,
    alias character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.authenticator_config OWNER TO admin;

--
-- Name: authenticator_config_entry; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.authenticator_config_entry (
    authenticator_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.authenticator_config_entry OWNER TO admin;

--
-- Name: broker_link; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.broker_link (
    identity_provider character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL,
    broker_user_id character varying(255),
    broker_username character varying(255),
    token text,
    user_id character varying(255) NOT NULL
);


ALTER TABLE public.broker_link OWNER TO admin;

--
-- Name: client; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client (
    id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    full_scope_allowed boolean DEFAULT false NOT NULL,
    client_id character varying(255),
    not_before integer,
    public_client boolean DEFAULT false NOT NULL,
    secret character varying(255),
    base_url character varying(255),
    bearer_only boolean DEFAULT false NOT NULL,
    management_url character varying(255),
    surrogate_auth_required boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    protocol character varying(255),
    node_rereg_timeout integer DEFAULT 0,
    frontchannel_logout boolean DEFAULT false NOT NULL,
    consent_required boolean DEFAULT false NOT NULL,
    name character varying(255),
    service_accounts_enabled boolean DEFAULT false NOT NULL,
    client_authenticator_type character varying(255),
    root_url character varying(255),
    description character varying(255),
    registration_token character varying(255),
    standard_flow_enabled boolean DEFAULT true NOT NULL,
    implicit_flow_enabled boolean DEFAULT false NOT NULL,
    direct_access_grants_enabled boolean DEFAULT false NOT NULL,
    always_display_in_console boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client OWNER TO admin;

--
-- Name: client_attributes; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_attributes (
    client_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.client_attributes OWNER TO admin;

--
-- Name: client_auth_flow_bindings; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_auth_flow_bindings (
    client_id character varying(36) NOT NULL,
    flow_id character varying(36),
    binding_name character varying(255) NOT NULL
);


ALTER TABLE public.client_auth_flow_bindings OWNER TO admin;

--
-- Name: client_initial_access; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_initial_access (
    id character varying(36) NOT NULL,
    realm_id character varying(36) NOT NULL,
    "timestamp" integer,
    expiration integer,
    count integer,
    remaining_count integer
);


ALTER TABLE public.client_initial_access OWNER TO admin;

--
-- Name: client_node_registrations; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_node_registrations (
    client_id character varying(36) NOT NULL,
    value integer,
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_node_registrations OWNER TO admin;

--
-- Name: client_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_scope (
    id character varying(36) NOT NULL,
    name character varying(255),
    realm_id character varying(36),
    description character varying(255),
    protocol character varying(255)
);


ALTER TABLE public.client_scope OWNER TO admin;

--
-- Name: client_scope_attributes; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_scope_attributes (
    scope_id character varying(36) NOT NULL,
    value character varying(2048),
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_scope_attributes OWNER TO admin;

--
-- Name: client_scope_client; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_scope_client (
    client_id character varying(255) NOT NULL,
    scope_id character varying(255) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client_scope_client OWNER TO admin;

--
-- Name: client_scope_role_mapping; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_scope_role_mapping (
    scope_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.client_scope_role_mapping OWNER TO admin;

--
-- Name: client_session; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_session (
    id character varying(36) NOT NULL,
    client_id character varying(36),
    redirect_uri character varying(255),
    state character varying(255),
    "timestamp" integer,
    session_id character varying(36),
    auth_method character varying(255),
    realm_id character varying(255),
    auth_user_id character varying(36),
    current_action character varying(36)
);


ALTER TABLE public.client_session OWNER TO admin;

--
-- Name: client_session_auth_status; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_session_auth_status (
    authenticator character varying(36) NOT NULL,
    status integer,
    client_session character varying(36) NOT NULL
);


ALTER TABLE public.client_session_auth_status OWNER TO admin;

--
-- Name: client_session_note; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_session_note (
    name character varying(255) NOT NULL,
    value character varying(255),
    client_session character varying(36) NOT NULL
);


ALTER TABLE public.client_session_note OWNER TO admin;

--
-- Name: client_session_prot_mapper; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_session_prot_mapper (
    protocol_mapper_id character varying(36) NOT NULL,
    client_session character varying(36) NOT NULL
);


ALTER TABLE public.client_session_prot_mapper OWNER TO admin;

--
-- Name: client_session_role; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_session_role (
    role_id character varying(255) NOT NULL,
    client_session character varying(36) NOT NULL
);


ALTER TABLE public.client_session_role OWNER TO admin;

--
-- Name: client_user_session_note; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_user_session_note (
    name character varying(255) NOT NULL,
    value character varying(2048),
    client_session character varying(36) NOT NULL
);


ALTER TABLE public.client_user_session_note OWNER TO admin;

--
-- Name: component; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.component (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_id character varying(36),
    provider_id character varying(36),
    provider_type character varying(255),
    realm_id character varying(36),
    sub_type character varying(255)
);


ALTER TABLE public.component OWNER TO admin;

--
-- Name: component_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.component_config (
    id character varying(36) NOT NULL,
    component_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.component_config OWNER TO admin;

--
-- Name: composite_role; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.composite_role (
    composite character varying(36) NOT NULL,
    child_role character varying(36) NOT NULL
);


ALTER TABLE public.composite_role OWNER TO admin;

--
-- Name: credential; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    user_id character varying(36),
    created_date bigint,
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE public.credential OWNER TO admin;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO admin;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO admin;

--
-- Name: default_client_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.default_client_scope (
    realm_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.default_client_scope OWNER TO admin;

--
-- Name: event_entity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.event_entity (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    details_json character varying(2550),
    error character varying(255),
    ip_address character varying(255),
    realm_id character varying(255),
    session_id character varying(255),
    event_time bigint,
    type character varying(255),
    user_id character varying(255),
    details_json_long_value text
);


ALTER TABLE public.event_entity OWNER TO admin;

--
-- Name: fed_user_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_attribute (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    value character varying(2024),
    long_value_hash bytea,
    long_value_hash_lower_case bytea,
    long_value text
);


ALTER TABLE public.fed_user_attribute OWNER TO admin;

--
-- Name: fed_user_consent; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.fed_user_consent OWNER TO admin;

--
-- Name: fed_user_consent_cl_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_consent_cl_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.fed_user_consent_cl_scope OWNER TO admin;

--
-- Name: fed_user_credential; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    created_date bigint,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE public.fed_user_credential OWNER TO admin;

--
-- Name: fed_user_group_membership; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_group_membership OWNER TO admin;

--
-- Name: fed_user_required_action; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_required_action (
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_required_action OWNER TO admin;

--
-- Name: fed_user_role_mapping; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_role_mapping (
    role_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_role_mapping OWNER TO admin;

--
-- Name: federated_identity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.federated_identity (
    identity_provider character varying(255) NOT NULL,
    realm_id character varying(36),
    federated_user_id character varying(255),
    federated_username character varying(255),
    token text,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_identity OWNER TO admin;

--
-- Name: federated_user; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.federated_user (
    id character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_user OWNER TO admin;

--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO admin;

--
-- Name: frames; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.frames (
    id uuid NOT NULL,
    story_id bigint,
    title character varying(255),
    text text,
    text_color character varying(255),
    picture_url character varying(255),
    visible_button_or_none character varying(255),
    button_text character varying(255),
    button_text_color character varying(255),
    button_background_color character varying(255),
    button_url character varying(255),
    gradient character varying(255),
    approved boolean
);


ALTER TABLE public.frames OWNER TO admin;

--
-- Name: group_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.group_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_attribute OWNER TO admin;

--
-- Name: group_role_mapping; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.group_role_mapping (
    role_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_role_mapping OWNER TO admin;

--
-- Name: history; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.history (
    id integer NOT NULL,
    component_type character varying(30),
    operation_type character varying(30),
    bank character varying(30),
    platform character varying(30),
    status character varying(30),
    component_id bigint,
    "time" date,
    user_name character varying(255)
);


ALTER TABLE public.history OWNER TO admin;

--
-- Name: history_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.history_id_seq OWNER TO admin;

--
-- Name: history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.history_id_seq OWNED BY public.history.id;


--
-- Name: identity_provider; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.identity_provider (
    internal_id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    provider_alias character varying(255),
    provider_id character varying(255),
    store_token boolean DEFAULT false NOT NULL,
    authenticate_by_default boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    add_token_role boolean DEFAULT true NOT NULL,
    trust_email boolean DEFAULT false NOT NULL,
    first_broker_login_flow_id character varying(36),
    post_broker_login_flow_id character varying(36),
    provider_display_name character varying(255),
    link_only boolean DEFAULT false NOT NULL
);


ALTER TABLE public.identity_provider OWNER TO admin;

--
-- Name: identity_provider_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.identity_provider_config (
    identity_provider_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.identity_provider_config OWNER TO admin;

--
-- Name: identity_provider_mapper; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.identity_provider_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    idp_alias character varying(255) NOT NULL,
    idp_mapper_name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.identity_provider_mapper OWNER TO admin;

--
-- Name: idp_mapper_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.idp_mapper_config (
    idp_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.idp_mapper_config OWNER TO admin;

--
-- Name: keycloak_group; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.keycloak_group (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_group character varying(36) NOT NULL,
    realm_id character varying(36)
);


ALTER TABLE public.keycloak_group OWNER TO admin;

--
-- Name: keycloak_role; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.keycloak_role (
    id character varying(36) NOT NULL,
    client_realm_constraint character varying(255),
    client_role boolean DEFAULT false NOT NULL,
    description character varying(255),
    name character varying(255),
    realm_id character varying(255),
    client character varying(36),
    realm character varying(36)
);


ALTER TABLE public.keycloak_role OWNER TO admin;

--
-- Name: migration_model; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.migration_model (
    id character varying(36) NOT NULL,
    version character varying(36),
    update_time bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.migration_model OWNER TO admin;

--
-- Name: offline_client_session; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.offline_client_session (
    user_session_id character varying(36) NOT NULL,
    client_id character varying(255) NOT NULL,
    offline_flag character varying(4) NOT NULL,
    "timestamp" integer,
    data text,
    client_storage_provider character varying(36) DEFAULT 'local'::character varying NOT NULL,
    external_client_id character varying(255) DEFAULT 'local'::character varying NOT NULL,
    version integer DEFAULT 0
);


ALTER TABLE public.offline_client_session OWNER TO admin;

--
-- Name: offline_user_session; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.offline_user_session (
    user_session_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    created_on integer NOT NULL,
    offline_flag character varying(4) NOT NULL,
    data text,
    last_session_refresh integer DEFAULT 0 NOT NULL,
    broker_session_id character varying(1024),
    version integer DEFAULT 0
);


ALTER TABLE public.offline_user_session OWNER TO admin;

--
-- Name: org; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.org (
    id character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    realm_id character varying(255) NOT NULL,
    group_id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(4000)
);


ALTER TABLE public.org OWNER TO admin;

--
-- Name: org_domain; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.org_domain (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    verified boolean NOT NULL,
    org_id character varying(255) NOT NULL
);


ALTER TABLE public.org_domain OWNER TO admin;

--
-- Name: policy_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.policy_config (
    policy_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.policy_config OWNER TO admin;

--
-- Name: protocol_mapper; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.protocol_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    protocol character varying(255) NOT NULL,
    protocol_mapper_name character varying(255) NOT NULL,
    client_id character varying(36),
    client_scope_id character varying(36)
);


ALTER TABLE public.protocol_mapper OWNER TO admin;

--
-- Name: protocol_mapper_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.protocol_mapper_config (
    protocol_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.protocol_mapper_config OWNER TO admin;

--
-- Name: realm; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm (
    id character varying(36) NOT NULL,
    access_code_lifespan integer,
    user_action_lifespan integer,
    access_token_lifespan integer,
    account_theme character varying(255),
    admin_theme character varying(255),
    email_theme character varying(255),
    enabled boolean DEFAULT false NOT NULL,
    events_enabled boolean DEFAULT false NOT NULL,
    events_expiration bigint,
    login_theme character varying(255),
    name character varying(255),
    not_before integer,
    password_policy character varying(2550),
    registration_allowed boolean DEFAULT false NOT NULL,
    remember_me boolean DEFAULT false NOT NULL,
    reset_password_allowed boolean DEFAULT false NOT NULL,
    social boolean DEFAULT false NOT NULL,
    ssl_required character varying(255),
    sso_idle_timeout integer,
    sso_max_lifespan integer,
    update_profile_on_soc_login boolean DEFAULT false NOT NULL,
    verify_email boolean DEFAULT false NOT NULL,
    master_admin_client character varying(36),
    login_lifespan integer,
    internationalization_enabled boolean DEFAULT false NOT NULL,
    default_locale character varying(255),
    reg_email_as_username boolean DEFAULT false NOT NULL,
    admin_events_enabled boolean DEFAULT false NOT NULL,
    admin_events_details_enabled boolean DEFAULT false NOT NULL,
    edit_username_allowed boolean DEFAULT false NOT NULL,
    otp_policy_counter integer DEFAULT 0,
    otp_policy_window integer DEFAULT 1,
    otp_policy_period integer DEFAULT 30,
    otp_policy_digits integer DEFAULT 6,
    otp_policy_alg character varying(36) DEFAULT 'HmacSHA1'::character varying,
    otp_policy_type character varying(36) DEFAULT 'totp'::character varying,
    browser_flow character varying(36),
    registration_flow character varying(36),
    direct_grant_flow character varying(36),
    reset_credentials_flow character varying(36),
    client_auth_flow character varying(36),
    offline_session_idle_timeout integer DEFAULT 0,
    revoke_refresh_token boolean DEFAULT false NOT NULL,
    access_token_life_implicit integer DEFAULT 0,
    login_with_email_allowed boolean DEFAULT true NOT NULL,
    duplicate_emails_allowed boolean DEFAULT false NOT NULL,
    docker_auth_flow character varying(36),
    refresh_token_max_reuse integer DEFAULT 0,
    allow_user_managed_access boolean DEFAULT false NOT NULL,
    sso_max_lifespan_remember_me integer DEFAULT 0 NOT NULL,
    sso_idle_timeout_remember_me integer DEFAULT 0 NOT NULL,
    default_role character varying(255)
);


ALTER TABLE public.realm OWNER TO admin;

--
-- Name: realm_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_attribute (
    name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    value text
);


ALTER TABLE public.realm_attribute OWNER TO admin;

--
-- Name: realm_default_groups; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_default_groups (
    realm_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_default_groups OWNER TO admin;

--
-- Name: realm_enabled_event_types; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_enabled_event_types (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_enabled_event_types OWNER TO admin;

--
-- Name: realm_events_listeners; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_events_listeners (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_events_listeners OWNER TO admin;

--
-- Name: realm_localizations; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_localizations (
    realm_id character varying(255) NOT NULL,
    locale character varying(255) NOT NULL,
    texts text NOT NULL
);


ALTER TABLE public.realm_localizations OWNER TO admin;

--
-- Name: realm_required_credential; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_required_credential (
    type character varying(255) NOT NULL,
    form_label character varying(255),
    input boolean DEFAULT false NOT NULL,
    secret boolean DEFAULT false NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_required_credential OWNER TO admin;

--
-- Name: realm_smtp_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_smtp_config (
    realm_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.realm_smtp_config OWNER TO admin;

--
-- Name: realm_supported_locales; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_supported_locales (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_supported_locales OWNER TO admin;

--
-- Name: redirect_uris; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.redirect_uris (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.redirect_uris OWNER TO admin;

--
-- Name: required_action_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.required_action_config (
    required_action_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.required_action_config OWNER TO admin;

--
-- Name: required_action_provider; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.required_action_provider (
    id character varying(36) NOT NULL,
    alias character varying(255),
    name character varying(255),
    realm_id character varying(36),
    enabled boolean DEFAULT false NOT NULL,
    default_action boolean DEFAULT false NOT NULL,
    provider_id character varying(255),
    priority integer
);


ALTER TABLE public.required_action_provider OWNER TO admin;

--
-- Name: resource_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    resource_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_attribute OWNER TO admin;

--
-- Name: resource_policy; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_policy (
    resource_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_policy OWNER TO admin;

--
-- Name: resource_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_scope (
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_scope OWNER TO admin;

--
-- Name: resource_server; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_server (
    id character varying(36) NOT NULL,
    allow_rs_remote_mgmt boolean DEFAULT false NOT NULL,
    policy_enforce_mode smallint NOT NULL,
    decision_strategy smallint DEFAULT 1 NOT NULL
);


ALTER TABLE public.resource_server OWNER TO admin;

--
-- Name: resource_server_perm_ticket; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_server_perm_ticket (
    id character varying(36) NOT NULL,
    owner character varying(255) NOT NULL,
    requester character varying(255) NOT NULL,
    created_timestamp bigint NOT NULL,
    granted_timestamp bigint,
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36),
    resource_server_id character varying(36) NOT NULL,
    policy_id character varying(36)
);


ALTER TABLE public.resource_server_perm_ticket OWNER TO admin;

--
-- Name: resource_server_policy; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_server_policy (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    type character varying(255) NOT NULL,
    decision_strategy smallint,
    logic smallint,
    resource_server_id character varying(36) NOT NULL,
    owner character varying(255)
);


ALTER TABLE public.resource_server_policy OWNER TO admin;

--
-- Name: resource_server_resource; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_server_resource (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(255),
    icon_uri character varying(255),
    owner character varying(255) NOT NULL,
    resource_server_id character varying(36) NOT NULL,
    owner_managed_access boolean DEFAULT false NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_resource OWNER TO admin;

--
-- Name: resource_server_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_server_scope (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    icon_uri character varying(255),
    resource_server_id character varying(36) NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_scope OWNER TO admin;

--
-- Name: resource_uris; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_uris (
    resource_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.resource_uris OWNER TO admin;

--
-- Name: role_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.role_attribute (
    id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255)
);


ALTER TABLE public.role_attribute OWNER TO admin;

--
-- Name: scope_mapping; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.scope_mapping (
    client_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_mapping OWNER TO admin;

--
-- Name: scope_policy; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.scope_policy (
    scope_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_policy OWNER TO admin;

--
-- Name: stories; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.stories (
    id integer NOT NULL,
    bank_id character varying(255),
    platform character varying(255),
    font_size character varying(255),
    preview_title character varying(255),
    preview_title_color character varying(255),
    preview_url character varying(255),
    preview_gradient character varying(255),
    approved character varying(15)
);


ALTER TABLE public.stories OWNER TO admin;

--
-- Name: stories_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.stories_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stories_id_seq OWNER TO admin;

--
-- Name: stories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.stories_id_seq OWNED BY public.stories.id;


--
-- Name: user_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_attribute (
    name character varying(255) NOT NULL,
    value character varying(255),
    user_id character varying(36) NOT NULL,
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    long_value_hash bytea,
    long_value_hash_lower_case bytea,
    long_value text
);


ALTER TABLE public.user_attribute OWNER TO admin;

--
-- Name: user_consent; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(36) NOT NULL,
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.user_consent OWNER TO admin;

--
-- Name: user_consent_client_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_consent_client_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.user_consent_client_scope OWNER TO admin;

--
-- Name: user_entity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_entity (
    id character varying(36) NOT NULL,
    email character varying(255),
    email_constraint character varying(255),
    email_verified boolean DEFAULT false NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    federation_link character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    realm_id character varying(255),
    username character varying(255),
    created_timestamp bigint,
    service_account_client_link character varying(255),
    not_before integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.user_entity OWNER TO admin;

--
-- Name: user_federation_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_federation_config (
    user_federation_provider_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_config OWNER TO admin;

--
-- Name: user_federation_mapper; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_federation_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    federation_provider_id character varying(36) NOT NULL,
    federation_mapper_type character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.user_federation_mapper OWNER TO admin;

--
-- Name: user_federation_mapper_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_federation_mapper_config (
    user_federation_mapper_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_mapper_config OWNER TO admin;

--
-- Name: user_federation_provider; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_federation_provider (
    id character varying(36) NOT NULL,
    changed_sync_period integer,
    display_name character varying(255),
    full_sync_period integer,
    last_sync integer,
    priority integer,
    provider_name character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.user_federation_provider OWNER TO admin;

--
-- Name: user_group_membership; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.user_group_membership OWNER TO admin;

--
-- Name: user_required_action; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_required_action (
    user_id character varying(36) NOT NULL,
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL
);


ALTER TABLE public.user_required_action OWNER TO admin;

--
-- Name: user_role_mapping; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_role_mapping (
    role_id character varying(255) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.user_role_mapping OWNER TO admin;

--
-- Name: user_session; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_session (
    id character varying(36) NOT NULL,
    auth_method character varying(255),
    ip_address character varying(255),
    last_session_refresh integer,
    login_username character varying(255),
    realm_id character varying(255),
    remember_me boolean DEFAULT false NOT NULL,
    started integer,
    user_id character varying(255),
    user_session_state integer,
    broker_session_id character varying(255),
    broker_user_id character varying(255)
);


ALTER TABLE public.user_session OWNER TO admin;

--
-- Name: user_session_note; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_session_note (
    user_session character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(2048)
);


ALTER TABLE public.user_session_note OWNER TO admin;

--
-- Name: username_login_failure; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.username_login_failure (
    realm_id character varying(36) NOT NULL,
    username character varying(255) NOT NULL,
    failed_login_not_before integer,
    last_failure bigint,
    last_ip_failure character varying(255),
    num_failures integer
);


ALTER TABLE public.username_login_failure OWNER TO admin;

--
-- Name: web_origins; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.web_origins (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.web_origins OWNER TO admin;

--
-- Name: history id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.history ALTER COLUMN id SET DEFAULT nextval('public.history_id_seq'::regclass);


--
-- Name: stories id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.stories ALTER COLUMN id SET DEFAULT nextval('public.stories_id_seq'::regclass);


--
-- Data for Name: admin_event_entity; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.admin_event_entity (id, admin_event_time, realm_id, operation_type, auth_realm_id, auth_client_id, auth_user_id, ip_address, resource_path, representation, error, resource_type) FROM stdin;
\.


--
-- Data for Name: associated_policy; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.associated_policy (policy_id, associated_policy_id) FROM stdin;
\.


--
-- Data for Name: authentication_execution; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authentication_execution (id, alias, authenticator, realm_id, flow_id, requirement, priority, authenticator_flow, auth_flow_id, auth_config) FROM stdin;
be37f94b-8ee1-486c-968b-f5ccef605558	\N	auth-cookie	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f58e92c0-92ac-454b-b0f3-701dab19cedd	2	10	f	\N	\N
858f56fb-db1f-4382-9f80-b529d1d0320f	\N	auth-spnego	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f58e92c0-92ac-454b-b0f3-701dab19cedd	3	20	f	\N	\N
167e5e7b-6281-46f5-9de2-6430b6515360	\N	identity-provider-redirector	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f58e92c0-92ac-454b-b0f3-701dab19cedd	2	25	f	\N	\N
a7478058-40ac-4ae2-84b0-825a6b9631e3	\N	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f58e92c0-92ac-454b-b0f3-701dab19cedd	2	30	t	b73b516b-a35a-432b-8385-5d378d2ee082	\N
52f5206a-baa0-4867-9bdc-d5bff9a03d1e	\N	auth-username-password-form	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	b73b516b-a35a-432b-8385-5d378d2ee082	0	10	f	\N	\N
04d6fdc6-4715-4a48-9189-36d53e481445	\N	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	b73b516b-a35a-432b-8385-5d378d2ee082	1	20	t	2eef655a-a887-4472-9a0e-4d4df7170f79	\N
c5a1d7cd-2e62-4df4-8bf6-0441f49795b5	\N	conditional-user-configured	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	2eef655a-a887-4472-9a0e-4d4df7170f79	0	10	f	\N	\N
4bbbaf89-35a9-48b7-94f0-53af1144b5b3	\N	auth-otp-form	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	2eef655a-a887-4472-9a0e-4d4df7170f79	0	20	f	\N	\N
ae1d55e4-0828-4eb5-8b2f-df97103acd86	\N	direct-grant-validate-username	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	cd87196c-2dee-4942-9c0b-def480ad5ea1	0	10	f	\N	\N
617502a4-fca7-41d4-bd9f-04b8dfc92378	\N	direct-grant-validate-password	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	cd87196c-2dee-4942-9c0b-def480ad5ea1	0	20	f	\N	\N
86e57e4a-246b-4b02-a5fa-4c87635efb21	\N	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	cd87196c-2dee-4942-9c0b-def480ad5ea1	1	30	t	f240d32c-7168-4785-ba3b-7ac09aa04412	\N
46bf9bdf-e9e5-490a-bbd5-34a2461acaab	\N	conditional-user-configured	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f240d32c-7168-4785-ba3b-7ac09aa04412	0	10	f	\N	\N
f8a58f87-7a39-4f9b-bbf5-bede87d25830	\N	direct-grant-validate-otp	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f240d32c-7168-4785-ba3b-7ac09aa04412	0	20	f	\N	\N
293f96f4-1b4e-40c5-9e53-76168479edfc	\N	registration-page-form	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	83df2d16-50d9-405a-9384-cd1b8c0d38e8	0	10	t	8dcb74a0-9c5b-4ff7-abaa-712a3f508c85	\N
cd395454-1daa-4f3c-aed9-02b2c30c414a	\N	registration-user-creation	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	8dcb74a0-9c5b-4ff7-abaa-712a3f508c85	0	20	f	\N	\N
0617b0b1-c834-45f2-9749-f66ca794e616	\N	registration-password-action	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	8dcb74a0-9c5b-4ff7-abaa-712a3f508c85	0	50	f	\N	\N
38a512ac-29dc-4010-abb3-a49f762cf5df	\N	registration-recaptcha-action	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	8dcb74a0-9c5b-4ff7-abaa-712a3f508c85	3	60	f	\N	\N
8f40a261-e08e-4e2d-afc3-e37fb445c7c2	\N	registration-terms-and-conditions	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	8dcb74a0-9c5b-4ff7-abaa-712a3f508c85	3	70	f	\N	\N
e04c9e6a-7a9e-42b2-9a1a-9fb18e18c373	\N	reset-credentials-choose-user	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	80f361c4-a499-4715-b346-58540a3acf10	0	10	f	\N	\N
ddc80ded-b185-491b-a09a-8c5b55ad2af7	\N	reset-credential-email	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	80f361c4-a499-4715-b346-58540a3acf10	0	20	f	\N	\N
ca47c996-03c3-4114-9ce3-3058ff56d193	\N	reset-password	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	80f361c4-a499-4715-b346-58540a3acf10	0	30	f	\N	\N
cd9411b5-4442-4301-beb0-f3576a49259d	\N	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	80f361c4-a499-4715-b346-58540a3acf10	1	40	t	80b4d70f-5c23-48a8-913b-a5f95376a3ff	\N
2e220f28-5144-48ad-ae13-7c5c230a21ed	\N	conditional-user-configured	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	80b4d70f-5c23-48a8-913b-a5f95376a3ff	0	10	f	\N	\N
1464d3b2-c9ae-417e-ad88-b9f5ed599707	\N	reset-otp	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	80b4d70f-5c23-48a8-913b-a5f95376a3ff	0	20	f	\N	\N
8e17190a-9976-4743-aa7f-302a56e4db85	\N	client-secret	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	1ac85d62-1942-4c16-85ab-5e95ee5ca5db	2	10	f	\N	\N
55eae7c8-20a2-4aeb-90ae-7bf927a1a28b	\N	client-jwt	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	1ac85d62-1942-4c16-85ab-5e95ee5ca5db	2	20	f	\N	\N
158f03b3-12b9-4ff4-9a54-2a42c53b2d5c	\N	client-secret-jwt	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	1ac85d62-1942-4c16-85ab-5e95ee5ca5db	2	30	f	\N	\N
08f37984-0845-4b1a-91de-81727580318c	\N	client-x509	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	1ac85d62-1942-4c16-85ab-5e95ee5ca5db	2	40	f	\N	\N
7089251a-504b-43ce-9075-54087007616f	\N	idp-review-profile	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	5a41776e-5520-4360-be9f-f1d49291a035	0	10	f	\N	500d77b8-576c-4465-bcfa-ddb014389e5c
7760f324-76b7-49e0-acc5-5762d58bd622	\N	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	5a41776e-5520-4360-be9f-f1d49291a035	0	20	t	c72edaf5-4327-45c7-8841-672c4e5ec51b	\N
12c141de-96c4-454e-b9bc-3b8eff5c93e7	\N	idp-create-user-if-unique	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	c72edaf5-4327-45c7-8841-672c4e5ec51b	2	10	f	\N	5bd5dd5b-0488-4358-919a-8d433e947982
08ea9757-5d7a-40e2-8c01-35f0420249e8	\N	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	c72edaf5-4327-45c7-8841-672c4e5ec51b	2	20	t	663fc537-9af5-4d83-9c30-a56d25ed60db	\N
26d9010b-fe98-4d1a-bf2d-11450924cf2d	\N	idp-confirm-link	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	663fc537-9af5-4d83-9c30-a56d25ed60db	0	10	f	\N	\N
3e159fec-4ca6-4412-ae0a-e3634d93d219	\N	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	663fc537-9af5-4d83-9c30-a56d25ed60db	0	20	t	ed640ce6-2dfa-4711-a77f-75225bbd2524	\N
bd6eb622-8a51-425d-aa17-86749ca0d7f5	\N	idp-email-verification	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	ed640ce6-2dfa-4711-a77f-75225bbd2524	2	10	f	\N	\N
7bfc5317-bc39-457c-abd3-af192cbcab2e	\N	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	ed640ce6-2dfa-4711-a77f-75225bbd2524	2	20	t	c0ae0562-2065-4630-b96f-30431575a496	\N
84951dcb-b247-486b-aa6f-2dc2db6e2382	\N	idp-username-password-form	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	c0ae0562-2065-4630-b96f-30431575a496	0	10	f	\N	\N
5bc5754e-cf10-43a5-bf01-99ba796dd31d	\N	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	c0ae0562-2065-4630-b96f-30431575a496	1	20	t	7017b54d-a0f6-4f78-8a38-2dda10682554	\N
f59611c3-cf71-4d9a-9e08-a9ff30d00281	\N	conditional-user-configured	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	7017b54d-a0f6-4f78-8a38-2dda10682554	0	10	f	\N	\N
89e9af80-34a5-4aa0-9cee-5e7bd5855241	\N	auth-otp-form	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	7017b54d-a0f6-4f78-8a38-2dda10682554	0	20	f	\N	\N
7156f1d2-eda7-4798-98b9-1084bd22227e	\N	http-basic-authenticator	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	5feb7daf-ad45-4abf-bd6c-b238c6029cbc	0	10	f	\N	\N
a5d07cf1-73f0-4ab5-9f9b-d35b18aabe2f	\N	docker-http-basic-authenticator	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	42e13f5b-15ca-47e1-a34b-95c30cf2f1ba	0	10	f	\N	\N
ea3ee25b-9778-4fc4-b448-936fd02800d1	\N	idp-email-verification	866b2156-2043-4bd6-8375-6cff30af44a0	b322f972-76cf-4bbf-ad60-446aa9d51c4f	2	10	f	\N	\N
95df98e6-df12-4117-a2f0-dbf0d1d8753d	\N	\N	866b2156-2043-4bd6-8375-6cff30af44a0	b322f972-76cf-4bbf-ad60-446aa9d51c4f	2	20	t	40902a3e-0872-4485-8856-efeb15b17247	\N
fd648ceb-786a-47d5-b70e-5691f06f2fff	\N	conditional-user-configured	866b2156-2043-4bd6-8375-6cff30af44a0	cf6939e6-9c92-40b8-a6d1-497aee3561d1	0	10	f	\N	\N
a06c9950-9557-47ca-9eee-fe0ba7989f99	\N	auth-otp-form	866b2156-2043-4bd6-8375-6cff30af44a0	cf6939e6-9c92-40b8-a6d1-497aee3561d1	0	20	f	\N	\N
a57d94e6-40c2-4f64-bb1c-927c7434d2fa	\N	conditional-user-configured	866b2156-2043-4bd6-8375-6cff30af44a0	9c65e359-64b4-42df-8560-fa1865f09d9c	0	10	f	\N	\N
14f63a05-d37d-4cbc-bc3d-0df708f6421a	\N	direct-grant-validate-otp	866b2156-2043-4bd6-8375-6cff30af44a0	9c65e359-64b4-42df-8560-fa1865f09d9c	0	20	f	\N	\N
62ae1f71-3677-442c-af60-a694b320270c	\N	conditional-user-configured	866b2156-2043-4bd6-8375-6cff30af44a0	d8575660-4659-4a2c-b3af-af696b76f543	0	10	f	\N	\N
55e2bc18-650c-4bce-99d1-60b7234104ca	\N	auth-otp-form	866b2156-2043-4bd6-8375-6cff30af44a0	d8575660-4659-4a2c-b3af-af696b76f543	0	20	f	\N	\N
49b2da95-8ca8-44f7-8d8e-831c44796f72	\N	idp-confirm-link	866b2156-2043-4bd6-8375-6cff30af44a0	21562678-7b94-426a-8062-7697b63d821a	0	10	f	\N	\N
754b53d3-72a7-4a29-9be1-d180c7817b66	\N	\N	866b2156-2043-4bd6-8375-6cff30af44a0	21562678-7b94-426a-8062-7697b63d821a	0	20	t	b322f972-76cf-4bbf-ad60-446aa9d51c4f	\N
52313bc0-379a-4160-b679-3551c59e353c	\N	conditional-user-configured	866b2156-2043-4bd6-8375-6cff30af44a0	3dbd4ea5-b4dd-4409-88ec-f63661660f37	0	10	f	\N	\N
f7e96471-74dc-46a6-b0e4-bfd7946cccf6	\N	reset-otp	866b2156-2043-4bd6-8375-6cff30af44a0	3dbd4ea5-b4dd-4409-88ec-f63661660f37	0	20	f	\N	\N
f1eaff59-551f-4ca2-94de-7bd14a9b05b1	\N	idp-create-user-if-unique	866b2156-2043-4bd6-8375-6cff30af44a0	48af7cd5-8366-4c2d-9707-61d4c1f050ba	2	10	f	\N	701e4de1-4910-43c1-bb8d-484c49fd25c5
bb51c229-4e7f-4dc8-a329-34994f2ba700	\N	\N	866b2156-2043-4bd6-8375-6cff30af44a0	48af7cd5-8366-4c2d-9707-61d4c1f050ba	2	20	t	21562678-7b94-426a-8062-7697b63d821a	\N
86535291-c5f4-4024-94b6-148dfc7cd5d4	\N	idp-username-password-form	866b2156-2043-4bd6-8375-6cff30af44a0	40902a3e-0872-4485-8856-efeb15b17247	0	10	f	\N	\N
e3e83af9-f154-45aa-b402-2d4886391564	\N	\N	866b2156-2043-4bd6-8375-6cff30af44a0	40902a3e-0872-4485-8856-efeb15b17247	1	20	t	d8575660-4659-4a2c-b3af-af696b76f543	\N
36005551-abb8-4c46-925e-da44993525c9	\N	auth-cookie	866b2156-2043-4bd6-8375-6cff30af44a0	edc14f70-88b4-4abe-8a7e-403ab91c992c	2	10	f	\N	\N
c174f78b-71f3-4888-a730-4bd0103010f2	\N	auth-spnego	866b2156-2043-4bd6-8375-6cff30af44a0	edc14f70-88b4-4abe-8a7e-403ab91c992c	3	20	f	\N	\N
959081ab-7818-42af-ba5c-77116b555397	\N	identity-provider-redirector	866b2156-2043-4bd6-8375-6cff30af44a0	edc14f70-88b4-4abe-8a7e-403ab91c992c	2	25	f	\N	\N
ad37e1a5-e58c-4797-8cba-daccc423ee60	\N	\N	866b2156-2043-4bd6-8375-6cff30af44a0	edc14f70-88b4-4abe-8a7e-403ab91c992c	2	30	t	77fb9c0d-994d-4cda-aaf8-b39754fb5518	\N
6352a97b-c929-4e66-989a-c1e886f415d5	\N	client-secret	866b2156-2043-4bd6-8375-6cff30af44a0	5ea9ca02-51ee-4849-8ea4-87aba5726bf5	2	10	f	\N	\N
df2717c3-5988-4179-94fe-f775cde5acec	\N	client-jwt	866b2156-2043-4bd6-8375-6cff30af44a0	5ea9ca02-51ee-4849-8ea4-87aba5726bf5	2	20	f	\N	\N
97eb85d9-330e-48ce-9216-26d02484e9cf	\N	client-secret-jwt	866b2156-2043-4bd6-8375-6cff30af44a0	5ea9ca02-51ee-4849-8ea4-87aba5726bf5	2	30	f	\N	\N
80b896d9-d465-437f-ba65-dd7e6c5be430	\N	client-x509	866b2156-2043-4bd6-8375-6cff30af44a0	5ea9ca02-51ee-4849-8ea4-87aba5726bf5	2	40	f	\N	\N
9701408b-0d88-455e-b710-6a8a91632ac1	\N	direct-grant-validate-username	866b2156-2043-4bd6-8375-6cff30af44a0	ecb82555-f2d3-4d58-b37c-1188bdad916d	0	10	f	\N	\N
2b67032b-d27f-4e5a-856d-888ca0d5033c	\N	direct-grant-validate-password	866b2156-2043-4bd6-8375-6cff30af44a0	ecb82555-f2d3-4d58-b37c-1188bdad916d	0	20	f	\N	\N
c60036d2-3dac-430e-a68b-10866eb033ac	\N	\N	866b2156-2043-4bd6-8375-6cff30af44a0	ecb82555-f2d3-4d58-b37c-1188bdad916d	1	30	t	9c65e359-64b4-42df-8560-fa1865f09d9c	\N
c92017ef-2d28-43bc-8333-3f0271d0b900	\N	docker-http-basic-authenticator	866b2156-2043-4bd6-8375-6cff30af44a0	2dbd67e5-c1b4-4a13-a153-d9a80effe420	0	10	f	\N	\N
3223ad8e-8de4-42e2-b5a5-2ed799fc9377	\N	idp-review-profile	866b2156-2043-4bd6-8375-6cff30af44a0	04761fa8-7184-4082-80cc-de40295f3aaf	0	10	f	\N	6e5727fb-70af-4966-8050-7aeab2cd768d
f3ebe644-0c43-4548-bb31-44c56ba6c3e9	\N	\N	866b2156-2043-4bd6-8375-6cff30af44a0	04761fa8-7184-4082-80cc-de40295f3aaf	0	20	t	48af7cd5-8366-4c2d-9707-61d4c1f050ba	\N
35f0f813-d20a-4e70-b90a-43049cdca597	\N	auth-username-password-form	866b2156-2043-4bd6-8375-6cff30af44a0	77fb9c0d-994d-4cda-aaf8-b39754fb5518	0	10	f	\N	\N
9c2df39b-d988-4fcb-8760-8d340a1ffb9a	\N	\N	866b2156-2043-4bd6-8375-6cff30af44a0	77fb9c0d-994d-4cda-aaf8-b39754fb5518	1	20	t	cf6939e6-9c92-40b8-a6d1-497aee3561d1	\N
39dd9d9e-7034-427a-bff8-5e7bdb4768cd	\N	registration-page-form	866b2156-2043-4bd6-8375-6cff30af44a0	09fe1617-ce32-412e-94be-789780df440d	0	10	t	5b78a1bf-69e4-40cb-8e9e-dcceaac71d13	\N
aa60aabc-9fc2-4aa6-ab20-562fcd122417	\N	registration-user-creation	866b2156-2043-4bd6-8375-6cff30af44a0	5b78a1bf-69e4-40cb-8e9e-dcceaac71d13	0	20	f	\N	\N
02def5e9-8ab4-4377-8b7e-43edbe7546bc	\N	registration-password-action	866b2156-2043-4bd6-8375-6cff30af44a0	5b78a1bf-69e4-40cb-8e9e-dcceaac71d13	0	50	f	\N	\N
fec840e3-a926-4e3d-a97e-987f2efb9c31	\N	registration-recaptcha-action	866b2156-2043-4bd6-8375-6cff30af44a0	5b78a1bf-69e4-40cb-8e9e-dcceaac71d13	3	60	f	\N	\N
6f9b7833-894d-4b9c-83ed-f94e72a4a2f3	\N	registration-terms-and-conditions	866b2156-2043-4bd6-8375-6cff30af44a0	5b78a1bf-69e4-40cb-8e9e-dcceaac71d13	3	70	f	\N	\N
db467a72-053f-4db5-aa37-b934b9d95702	\N	reset-credentials-choose-user	866b2156-2043-4bd6-8375-6cff30af44a0	00a2b84f-2720-4adf-a494-9b34222b35d7	0	10	f	\N	\N
8cc15ebe-29ca-487b-a138-fb321208486c	\N	reset-credential-email	866b2156-2043-4bd6-8375-6cff30af44a0	00a2b84f-2720-4adf-a494-9b34222b35d7	0	20	f	\N	\N
918da237-b1d7-4cc1-853c-5b1ef284f253	\N	reset-password	866b2156-2043-4bd6-8375-6cff30af44a0	00a2b84f-2720-4adf-a494-9b34222b35d7	0	30	f	\N	\N
c21357c6-b5aa-470d-a588-1eac7e3ddf3a	\N	\N	866b2156-2043-4bd6-8375-6cff30af44a0	00a2b84f-2720-4adf-a494-9b34222b35d7	1	40	t	3dbd4ea5-b4dd-4409-88ec-f63661660f37	\N
45b3ad76-1344-4463-b2e2-26fcb643225c	\N	http-basic-authenticator	866b2156-2043-4bd6-8375-6cff30af44a0	db9f2d09-8443-46b5-8464-f671f188a440	0	10	f	\N	\N
\.


--
-- Data for Name: authentication_flow; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authentication_flow (id, alias, description, realm_id, provider_id, top_level, built_in) FROM stdin;
f58e92c0-92ac-454b-b0f3-701dab19cedd	browser	browser based authentication	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	t	t
b73b516b-a35a-432b-8385-5d378d2ee082	forms	Username, password, otp and other auth forms.	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	f	t
2eef655a-a887-4472-9a0e-4d4df7170f79	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	f	t
cd87196c-2dee-4942-9c0b-def480ad5ea1	direct grant	OpenID Connect Resource Owner Grant	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	t	t
f240d32c-7168-4785-ba3b-7ac09aa04412	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	f	t
83df2d16-50d9-405a-9384-cd1b8c0d38e8	registration	registration flow	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	t	t
8dcb74a0-9c5b-4ff7-abaa-712a3f508c85	registration form	registration form	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	form-flow	f	t
80f361c4-a499-4715-b346-58540a3acf10	reset credentials	Reset credentials for a user if they forgot their password or something	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	t	t
80b4d70f-5c23-48a8-913b-a5f95376a3ff	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	f	t
1ac85d62-1942-4c16-85ab-5e95ee5ca5db	clients	Base authentication for clients	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	client-flow	t	t
5a41776e-5520-4360-be9f-f1d49291a035	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	t	t
c72edaf5-4327-45c7-8841-672c4e5ec51b	User creation or linking	Flow for the existing/non-existing user alternatives	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	f	t
663fc537-9af5-4d83-9c30-a56d25ed60db	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	f	t
ed640ce6-2dfa-4711-a77f-75225bbd2524	Account verification options	Method with which to verity the existing account	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	f	t
c0ae0562-2065-4630-b96f-30431575a496	Verify Existing Account by Re-authentication	Reauthentication of existing account	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	f	t
7017b54d-a0f6-4f78-8a38-2dda10682554	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	f	t
5feb7daf-ad45-4abf-bd6c-b238c6029cbc	saml ecp	SAML ECP Profile Authentication Flow	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	t	t
42e13f5b-15ca-47e1-a34b-95c30cf2f1ba	docker auth	Used by Docker clients to authenticate against the IDP	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	basic-flow	t	t
b322f972-76cf-4bbf-ad60-446aa9d51c4f	Account verification options	Method with which to verity the existing account	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	f	t
cf6939e6-9c92-40b8-a6d1-497aee3561d1	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	f	t
9c65e359-64b4-42df-8560-fa1865f09d9c	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	f	t
d8575660-4659-4a2c-b3af-af696b76f543	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	f	t
21562678-7b94-426a-8062-7697b63d821a	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	f	t
3dbd4ea5-b4dd-4409-88ec-f63661660f37	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	f	t
48af7cd5-8366-4c2d-9707-61d4c1f050ba	User creation or linking	Flow for the existing/non-existing user alternatives	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	f	t
40902a3e-0872-4485-8856-efeb15b17247	Verify Existing Account by Re-authentication	Reauthentication of existing account	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	f	t
edc14f70-88b4-4abe-8a7e-403ab91c992c	browser	browser based authentication	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	t	t
5ea9ca02-51ee-4849-8ea4-87aba5726bf5	clients	Base authentication for clients	866b2156-2043-4bd6-8375-6cff30af44a0	client-flow	t	t
ecb82555-f2d3-4d58-b37c-1188bdad916d	direct grant	OpenID Connect Resource Owner Grant	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	t	t
2dbd67e5-c1b4-4a13-a153-d9a80effe420	docker auth	Used by Docker clients to authenticate against the IDP	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	t	t
04761fa8-7184-4082-80cc-de40295f3aaf	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	t	t
77fb9c0d-994d-4cda-aaf8-b39754fb5518	forms	Username, password, otp and other auth forms.	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	f	t
09fe1617-ce32-412e-94be-789780df440d	registration	registration flow	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	t	t
5b78a1bf-69e4-40cb-8e9e-dcceaac71d13	registration form	registration form	866b2156-2043-4bd6-8375-6cff30af44a0	form-flow	f	t
00a2b84f-2720-4adf-a494-9b34222b35d7	reset credentials	Reset credentials for a user if they forgot their password or something	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	t	t
db9f2d09-8443-46b5-8464-f671f188a440	saml ecp	SAML ECP Profile Authentication Flow	866b2156-2043-4bd6-8375-6cff30af44a0	basic-flow	t	t
\.


--
-- Data for Name: authenticator_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authenticator_config (id, alias, realm_id) FROM stdin;
500d77b8-576c-4465-bcfa-ddb014389e5c	review profile config	54bbc134-2bbd-41a2-bd63-2f1b765cd38a
5bd5dd5b-0488-4358-919a-8d433e947982	create unique user config	54bbc134-2bbd-41a2-bd63-2f1b765cd38a
701e4de1-4910-43c1-bb8d-484c49fd25c5	create unique user config	866b2156-2043-4bd6-8375-6cff30af44a0
6e5727fb-70af-4966-8050-7aeab2cd768d	review profile config	866b2156-2043-4bd6-8375-6cff30af44a0
\.


--
-- Data for Name: authenticator_config_entry; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authenticator_config_entry (authenticator_id, value, name) FROM stdin;
500d77b8-576c-4465-bcfa-ddb014389e5c	missing	update.profile.on.first.login
5bd5dd5b-0488-4358-919a-8d433e947982	false	require.password.update.after.registration
6e5727fb-70af-4966-8050-7aeab2cd768d	missing	update.profile.on.first.login
701e4de1-4910-43c1-bb8d-484c49fd25c5	false	require.password.update.after.registration
\.


--
-- Data for Name: broker_link; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.broker_link (identity_provider, storage_provider_id, realm_id, broker_user_id, broker_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client (id, enabled, full_scope_allowed, client_id, not_before, public_client, secret, base_url, bearer_only, management_url, surrogate_auth_required, realm_id, protocol, node_rereg_timeout, frontchannel_logout, consent_required, name, service_accounts_enabled, client_authenticator_type, root_url, description, registration_token, standard_flow_enabled, implicit_flow_enabled, direct_access_grants_enabled, always_display_in_console) FROM stdin;
52a54993-a1a5-461a-bfe3-f27e68d50632	t	f	master-realm	0	f	\N	\N	t	\N	f	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N	0	f	f	master Realm	f	client-secret	\N	\N	\N	t	f	f	f
b13f0d0f-58f7-4834-b608-a1ffb579c343	t	f	account	0	t	\N	/realms/master/account/	f	\N	f	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
6be14e92-ab08-4735-a7c3-9077efc21122	t	f	account-console	0	t	\N	/realms/master/account/	f	\N	f	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
ac6afdec-f375-4494-8335-a581c2d581a7	t	f	broker	0	f	\N	\N	t	\N	f	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
42284954-6f22-48fe-b901-e61519350f85	t	f	security-admin-console	0	t	\N	/admin/master/console/	f	\N	f	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
d914fdf7-0270-4631-a9c2-c280633d2380	t	f	admin-cli	0	t	\N	\N	f	\N	f	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
496a27a9-032c-4976-addd-ee39086ad486	t	f	content-maker-realm	0	f	\N	\N	t	\N	f	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N	0	f	f	content-maker Realm	f	client-secret	\N	\N	\N	t	f	f	f
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	t	f	account-console	0	t	\N	/realms/content-maker/account/	f	\N	f	866b2156-2043-4bd6-8375-6cff30af44a0	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
b46397d7-5fed-4d25-be0d-2957ef5534ec	t	f	account	0	t	\N	/realms/content-maker/account/	f	\N	f	866b2156-2043-4bd6-8375-6cff30af44a0	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	t	f	admin-cli	0	t	\N	\N	f	\N	f	866b2156-2043-4bd6-8375-6cff30af44a0	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	t	t	makerFront	0	t	\N		f	http://localhost:3000/*	f	866b2156-2043-4bd6-8375-6cff30af44a0	openid-connect	-1	t	f		f	client-secret	http://localhost:3000		\N	t	f	t	f
a685d671-6d25-440d-a108-2678b8b9c297	t	f	broker	0	f	\N	\N	t	\N	f	866b2156-2043-4bd6-8375-6cff30af44a0	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	f	realm-management	0	f	\N	\N	t	\N	f	866b2156-2043-4bd6-8375-6cff30af44a0	openid-connect	0	f	f	${client_realm-management}	f	client-secret	\N	\N	\N	t	f	f	f
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	t	f	security-admin-console	0	t	\N	/admin/content-maker/console/	f	\N	f	866b2156-2043-4bd6-8375-6cff30af44a0	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
22cd5734-9234-4009-8349-809a8168d548	t	t	maker	0	f	**********		f	http://localhost:8080	f	866b2156-2043-4bd6-8375-6cff30af44a0	openid-connect	-1	t	f		f	client-secret	http://localhost:8080		\N	t	f	t	f
\.


--
-- Data for Name: client_attributes; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_attributes (client_id, name, value) FROM stdin;
b13f0d0f-58f7-4834-b608-a1ffb579c343	post.logout.redirect.uris	+
6be14e92-ab08-4735-a7c3-9077efc21122	post.logout.redirect.uris	+
6be14e92-ab08-4735-a7c3-9077efc21122	pkce.code.challenge.method	S256
42284954-6f22-48fe-b901-e61519350f85	post.logout.redirect.uris	+
42284954-6f22-48fe-b901-e61519350f85	pkce.code.challenge.method	S256
b46397d7-5fed-4d25-be0d-2957ef5534ec	post.logout.redirect.uris	+
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	post.logout.redirect.uris	+
a685d671-6d25-440d-a108-2678b8b9c297	post.logout.redirect.uris	+
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	post.logout.redirect.uris	+
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	post.logout.redirect.uris	+
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	pkce.code.challenge.method	S256
22cd5734-9234-4009-8349-809a8168d548	client.secret.creation.time	1717818851
22cd5734-9234-4009-8349-809a8168d548	token.endpoint.auth.signing.alg	HS256
22cd5734-9234-4009-8349-809a8168d548	post.logout.redirect.uris	+
22cd5734-9234-4009-8349-809a8168d548	oauth2.device.authorization.grant.enabled	true
22cd5734-9234-4009-8349-809a8168d548	backchannel.logout.revoke.offline.tokens	false
22cd5734-9234-4009-8349-809a8168d548	use.refresh.tokens	true
22cd5734-9234-4009-8349-809a8168d548	oidc.ciba.grant.enabled	false
22cd5734-9234-4009-8349-809a8168d548	client.use.lightweight.access.token.enabled	false
22cd5734-9234-4009-8349-809a8168d548	backchannel.logout.session.required	true
22cd5734-9234-4009-8349-809a8168d548	client_credentials.use_refresh_token	false
22cd5734-9234-4009-8349-809a8168d548	acr.loa.map	{}
22cd5734-9234-4009-8349-809a8168d548	require.pushed.authorization.requests	false
22cd5734-9234-4009-8349-809a8168d548	tls.client.certificate.bound.access.tokens	false
22cd5734-9234-4009-8349-809a8168d548	display.on.consent.screen	false
22cd5734-9234-4009-8349-809a8168d548	token.response.type.bearer.lower-case	false
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	post.logout.redirect.uris	+
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	pkce.code.challenge.method	S256
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	oidc.ciba.grant.enabled	false
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	backchannel.logout.session.required	true
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	display.on.consent.screen	false
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	oauth2.device.authorization.grant.enabled	false
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	backchannel.logout.revoke.offline.tokens	false
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	use.refresh.tokens	true
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	client_credentials.use_refresh_token	false
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	token.response.type.bearer.lower-case	false
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	access.token.lifespan	600
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	client.session.idle.timeout	2400
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	client.session.max.lifespan	7200
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	tls.client.certificate.bound.access.tokens	false
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	require.pushed.authorization.requests	false
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	client.use.lightweight.access.token.enabled	false
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	client.introspection.response.allow.jwt.claim.enabled	false
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	acr.loa.map	{}
\.


--
-- Data for Name: client_auth_flow_bindings; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_auth_flow_bindings (client_id, flow_id, binding_name) FROM stdin;
\.


--
-- Data for Name: client_initial_access; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_initial_access (id, realm_id, "timestamp", expiration, count, remaining_count) FROM stdin;
\.


--
-- Data for Name: client_node_registrations; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_node_registrations (client_id, value, name) FROM stdin;
\.


--
-- Data for Name: client_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope (id, name, realm_id, description, protocol) FROM stdin;
c914166f-3fea-4dd1-9f2f-734f44786eae	offline_access	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	OpenID Connect built-in scope: offline_access	openid-connect
1f9ab463-80c8-439f-8b68-ae21db071a79	role_list	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	SAML role list	saml
f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	profile	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	OpenID Connect built-in scope: profile	openid-connect
6a0c390a-ffcd-4a17-84fa-49f8c008da5b	email	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	OpenID Connect built-in scope: email	openid-connect
6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	address	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	OpenID Connect built-in scope: address	openid-connect
a2926530-4de4-4142-9f5a-742378581907	phone	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	OpenID Connect built-in scope: phone	openid-connect
551948ef-5aa4-4722-b08e-c1b1c5229ba2	roles	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	OpenID Connect scope for add user roles to the access token	openid-connect
32e61767-6d89-4cbc-b3e9-096b8a3ad949	web-origins	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	OpenID Connect scope for add allowed web origins to the access token	openid-connect
2b912d83-5c2e-44fe-b54a-f72d7c0ff846	microprofile-jwt	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	Microprofile - JWT built-in scope	openid-connect
61e59ead-9860-41f5-86b0-f766aa40b10f	acr	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd	basic	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	OpenID Connect scope for add all basic claims to the token	openid-connect
32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	profile	866b2156-2043-4bd6-8375-6cff30af44a0	OpenID Connect built-in scope: profile	openid-connect
43388d0a-5526-4134-8e1d-3319eb820949	web-origins	866b2156-2043-4bd6-8375-6cff30af44a0	OpenID Connect scope for add allowed web origins to the access token	openid-connect
b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	address	866b2156-2043-4bd6-8375-6cff30af44a0	OpenID Connect built-in scope: address	openid-connect
f00a2f72-cf43-4d39-a353-4fdc37d3effa	roles	866b2156-2043-4bd6-8375-6cff30af44a0	OpenID Connect scope for add user roles to the access token	openid-connect
7a6abf2a-7d54-4ca7-8037-725a309622a3	role_list	866b2156-2043-4bd6-8375-6cff30af44a0	SAML role list	saml
bbc3bdaf-213d-4365-a42c-40040a44d8b0	microprofile-jwt	866b2156-2043-4bd6-8375-6cff30af44a0	Microprofile - JWT built-in scope	openid-connect
edbf0dc9-24d0-471e-ab26-a740cc91bb9c	acr	866b2156-2043-4bd6-8375-6cff30af44a0	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
b0295366-0ab4-4bb8-9da7-586cfa842c92	email	866b2156-2043-4bd6-8375-6cff30af44a0	OpenID Connect built-in scope: email	openid-connect
fe70e32a-bc64-4c88-8533-b86c2a3bdba0	offline_access	866b2156-2043-4bd6-8375-6cff30af44a0	OpenID Connect built-in scope: offline_access	openid-connect
19a9cc74-7a24-4000-acea-2952b7b055ca	phone	866b2156-2043-4bd6-8375-6cff30af44a0	OpenID Connect built-in scope: phone	openid-connect
2d96c814-8a3d-45c1-be46-05be9fc4f34d	basic	866b2156-2043-4bd6-8375-6cff30af44a0	OpenID Connect scope for add all basic claims to the token	openid-connect
\.


--
-- Data for Name: client_scope_attributes; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope_attributes (scope_id, value, name) FROM stdin;
c914166f-3fea-4dd1-9f2f-734f44786eae	true	display.on.consent.screen
c914166f-3fea-4dd1-9f2f-734f44786eae	${offlineAccessScopeConsentText}	consent.screen.text
1f9ab463-80c8-439f-8b68-ae21db071a79	true	display.on.consent.screen
1f9ab463-80c8-439f-8b68-ae21db071a79	${samlRoleListScopeConsentText}	consent.screen.text
f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	true	display.on.consent.screen
f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	${profileScopeConsentText}	consent.screen.text
f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	true	include.in.token.scope
6a0c390a-ffcd-4a17-84fa-49f8c008da5b	true	display.on.consent.screen
6a0c390a-ffcd-4a17-84fa-49f8c008da5b	${emailScopeConsentText}	consent.screen.text
6a0c390a-ffcd-4a17-84fa-49f8c008da5b	true	include.in.token.scope
6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	true	display.on.consent.screen
6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	${addressScopeConsentText}	consent.screen.text
6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	true	include.in.token.scope
a2926530-4de4-4142-9f5a-742378581907	true	display.on.consent.screen
a2926530-4de4-4142-9f5a-742378581907	${phoneScopeConsentText}	consent.screen.text
a2926530-4de4-4142-9f5a-742378581907	true	include.in.token.scope
551948ef-5aa4-4722-b08e-c1b1c5229ba2	true	display.on.consent.screen
551948ef-5aa4-4722-b08e-c1b1c5229ba2	${rolesScopeConsentText}	consent.screen.text
551948ef-5aa4-4722-b08e-c1b1c5229ba2	false	include.in.token.scope
32e61767-6d89-4cbc-b3e9-096b8a3ad949	false	display.on.consent.screen
32e61767-6d89-4cbc-b3e9-096b8a3ad949		consent.screen.text
32e61767-6d89-4cbc-b3e9-096b8a3ad949	false	include.in.token.scope
2b912d83-5c2e-44fe-b54a-f72d7c0ff846	false	display.on.consent.screen
2b912d83-5c2e-44fe-b54a-f72d7c0ff846	true	include.in.token.scope
61e59ead-9860-41f5-86b0-f766aa40b10f	false	display.on.consent.screen
61e59ead-9860-41f5-86b0-f766aa40b10f	false	include.in.token.scope
ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd	false	display.on.consent.screen
ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd	false	include.in.token.scope
32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	true	include.in.token.scope
32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	true	display.on.consent.screen
32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	${profileScopeConsentText}	consent.screen.text
43388d0a-5526-4134-8e1d-3319eb820949	false	include.in.token.scope
43388d0a-5526-4134-8e1d-3319eb820949	false	display.on.consent.screen
43388d0a-5526-4134-8e1d-3319eb820949		consent.screen.text
b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	true	include.in.token.scope
b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	true	display.on.consent.screen
b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	${addressScopeConsentText}	consent.screen.text
f00a2f72-cf43-4d39-a353-4fdc37d3effa	false	include.in.token.scope
f00a2f72-cf43-4d39-a353-4fdc37d3effa	true	display.on.consent.screen
f00a2f72-cf43-4d39-a353-4fdc37d3effa	${rolesScopeConsentText}	consent.screen.text
7a6abf2a-7d54-4ca7-8037-725a309622a3	${samlRoleListScopeConsentText}	consent.screen.text
7a6abf2a-7d54-4ca7-8037-725a309622a3	true	display.on.consent.screen
bbc3bdaf-213d-4365-a42c-40040a44d8b0	true	include.in.token.scope
bbc3bdaf-213d-4365-a42c-40040a44d8b0	false	display.on.consent.screen
edbf0dc9-24d0-471e-ab26-a740cc91bb9c	false	include.in.token.scope
edbf0dc9-24d0-471e-ab26-a740cc91bb9c	false	display.on.consent.screen
b0295366-0ab4-4bb8-9da7-586cfa842c92	true	include.in.token.scope
b0295366-0ab4-4bb8-9da7-586cfa842c92	true	display.on.consent.screen
b0295366-0ab4-4bb8-9da7-586cfa842c92	${emailScopeConsentText}	consent.screen.text
fe70e32a-bc64-4c88-8533-b86c2a3bdba0	${offlineAccessScopeConsentText}	consent.screen.text
fe70e32a-bc64-4c88-8533-b86c2a3bdba0	true	display.on.consent.screen
19a9cc74-7a24-4000-acea-2952b7b055ca	true	include.in.token.scope
19a9cc74-7a24-4000-acea-2952b7b055ca	true	display.on.consent.screen
19a9cc74-7a24-4000-acea-2952b7b055ca	${phoneScopeConsentText}	consent.screen.text
2d96c814-8a3d-45c1-be46-05be9fc4f34d	false	display.on.consent.screen
2d96c814-8a3d-45c1-be46-05be9fc4f34d	false	include.in.token.scope
\.


--
-- Data for Name: client_scope_client; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope_client (client_id, scope_id, default_scope) FROM stdin;
b13f0d0f-58f7-4834-b608-a1ffb579c343	551948ef-5aa4-4722-b08e-c1b1c5229ba2	t
b13f0d0f-58f7-4834-b608-a1ffb579c343	32e61767-6d89-4cbc-b3e9-096b8a3ad949	t
b13f0d0f-58f7-4834-b608-a1ffb579c343	ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd	t
b13f0d0f-58f7-4834-b608-a1ffb579c343	6a0c390a-ffcd-4a17-84fa-49f8c008da5b	t
b13f0d0f-58f7-4834-b608-a1ffb579c343	61e59ead-9860-41f5-86b0-f766aa40b10f	t
b13f0d0f-58f7-4834-b608-a1ffb579c343	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	t
b13f0d0f-58f7-4834-b608-a1ffb579c343	2b912d83-5c2e-44fe-b54a-f72d7c0ff846	f
b13f0d0f-58f7-4834-b608-a1ffb579c343	6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	f
b13f0d0f-58f7-4834-b608-a1ffb579c343	a2926530-4de4-4142-9f5a-742378581907	f
b13f0d0f-58f7-4834-b608-a1ffb579c343	c914166f-3fea-4dd1-9f2f-734f44786eae	f
6be14e92-ab08-4735-a7c3-9077efc21122	551948ef-5aa4-4722-b08e-c1b1c5229ba2	t
6be14e92-ab08-4735-a7c3-9077efc21122	32e61767-6d89-4cbc-b3e9-096b8a3ad949	t
6be14e92-ab08-4735-a7c3-9077efc21122	ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd	t
6be14e92-ab08-4735-a7c3-9077efc21122	6a0c390a-ffcd-4a17-84fa-49f8c008da5b	t
6be14e92-ab08-4735-a7c3-9077efc21122	61e59ead-9860-41f5-86b0-f766aa40b10f	t
6be14e92-ab08-4735-a7c3-9077efc21122	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	t
6be14e92-ab08-4735-a7c3-9077efc21122	2b912d83-5c2e-44fe-b54a-f72d7c0ff846	f
6be14e92-ab08-4735-a7c3-9077efc21122	6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	f
6be14e92-ab08-4735-a7c3-9077efc21122	a2926530-4de4-4142-9f5a-742378581907	f
6be14e92-ab08-4735-a7c3-9077efc21122	c914166f-3fea-4dd1-9f2f-734f44786eae	f
d914fdf7-0270-4631-a9c2-c280633d2380	551948ef-5aa4-4722-b08e-c1b1c5229ba2	t
d914fdf7-0270-4631-a9c2-c280633d2380	32e61767-6d89-4cbc-b3e9-096b8a3ad949	t
d914fdf7-0270-4631-a9c2-c280633d2380	ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd	t
d914fdf7-0270-4631-a9c2-c280633d2380	6a0c390a-ffcd-4a17-84fa-49f8c008da5b	t
d914fdf7-0270-4631-a9c2-c280633d2380	61e59ead-9860-41f5-86b0-f766aa40b10f	t
d914fdf7-0270-4631-a9c2-c280633d2380	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	t
d914fdf7-0270-4631-a9c2-c280633d2380	2b912d83-5c2e-44fe-b54a-f72d7c0ff846	f
d914fdf7-0270-4631-a9c2-c280633d2380	6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	f
d914fdf7-0270-4631-a9c2-c280633d2380	a2926530-4de4-4142-9f5a-742378581907	f
d914fdf7-0270-4631-a9c2-c280633d2380	c914166f-3fea-4dd1-9f2f-734f44786eae	f
ac6afdec-f375-4494-8335-a581c2d581a7	551948ef-5aa4-4722-b08e-c1b1c5229ba2	t
ac6afdec-f375-4494-8335-a581c2d581a7	32e61767-6d89-4cbc-b3e9-096b8a3ad949	t
ac6afdec-f375-4494-8335-a581c2d581a7	ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd	t
ac6afdec-f375-4494-8335-a581c2d581a7	6a0c390a-ffcd-4a17-84fa-49f8c008da5b	t
ac6afdec-f375-4494-8335-a581c2d581a7	61e59ead-9860-41f5-86b0-f766aa40b10f	t
ac6afdec-f375-4494-8335-a581c2d581a7	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	t
ac6afdec-f375-4494-8335-a581c2d581a7	2b912d83-5c2e-44fe-b54a-f72d7c0ff846	f
ac6afdec-f375-4494-8335-a581c2d581a7	6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	f
ac6afdec-f375-4494-8335-a581c2d581a7	a2926530-4de4-4142-9f5a-742378581907	f
ac6afdec-f375-4494-8335-a581c2d581a7	c914166f-3fea-4dd1-9f2f-734f44786eae	f
52a54993-a1a5-461a-bfe3-f27e68d50632	551948ef-5aa4-4722-b08e-c1b1c5229ba2	t
52a54993-a1a5-461a-bfe3-f27e68d50632	32e61767-6d89-4cbc-b3e9-096b8a3ad949	t
52a54993-a1a5-461a-bfe3-f27e68d50632	ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd	t
52a54993-a1a5-461a-bfe3-f27e68d50632	6a0c390a-ffcd-4a17-84fa-49f8c008da5b	t
52a54993-a1a5-461a-bfe3-f27e68d50632	61e59ead-9860-41f5-86b0-f766aa40b10f	t
52a54993-a1a5-461a-bfe3-f27e68d50632	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	t
52a54993-a1a5-461a-bfe3-f27e68d50632	2b912d83-5c2e-44fe-b54a-f72d7c0ff846	f
52a54993-a1a5-461a-bfe3-f27e68d50632	6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	f
52a54993-a1a5-461a-bfe3-f27e68d50632	a2926530-4de4-4142-9f5a-742378581907	f
52a54993-a1a5-461a-bfe3-f27e68d50632	c914166f-3fea-4dd1-9f2f-734f44786eae	f
42284954-6f22-48fe-b901-e61519350f85	551948ef-5aa4-4722-b08e-c1b1c5229ba2	t
42284954-6f22-48fe-b901-e61519350f85	32e61767-6d89-4cbc-b3e9-096b8a3ad949	t
42284954-6f22-48fe-b901-e61519350f85	ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd	t
42284954-6f22-48fe-b901-e61519350f85	6a0c390a-ffcd-4a17-84fa-49f8c008da5b	t
42284954-6f22-48fe-b901-e61519350f85	61e59ead-9860-41f5-86b0-f766aa40b10f	t
42284954-6f22-48fe-b901-e61519350f85	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	t
42284954-6f22-48fe-b901-e61519350f85	2b912d83-5c2e-44fe-b54a-f72d7c0ff846	f
42284954-6f22-48fe-b901-e61519350f85	6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	f
42284954-6f22-48fe-b901-e61519350f85	a2926530-4de4-4142-9f5a-742378581907	f
42284954-6f22-48fe-b901-e61519350f85	c914166f-3fea-4dd1-9f2f-734f44786eae	f
b46397d7-5fed-4d25-be0d-2957ef5534ec	43388d0a-5526-4134-8e1d-3319eb820949	t
b46397d7-5fed-4d25-be0d-2957ef5534ec	edbf0dc9-24d0-471e-ab26-a740cc91bb9c	t
b46397d7-5fed-4d25-be0d-2957ef5534ec	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	t
b46397d7-5fed-4d25-be0d-2957ef5534ec	f00a2f72-cf43-4d39-a353-4fdc37d3effa	t
b46397d7-5fed-4d25-be0d-2957ef5534ec	b0295366-0ab4-4bb8-9da7-586cfa842c92	t
b46397d7-5fed-4d25-be0d-2957ef5534ec	b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	f
b46397d7-5fed-4d25-be0d-2957ef5534ec	19a9cc74-7a24-4000-acea-2952b7b055ca	f
b46397d7-5fed-4d25-be0d-2957ef5534ec	fe70e32a-bc64-4c88-8533-b86c2a3bdba0	f
b46397d7-5fed-4d25-be0d-2957ef5534ec	bbc3bdaf-213d-4365-a42c-40040a44d8b0	f
22cd5734-9234-4009-8349-809a8168d548	43388d0a-5526-4134-8e1d-3319eb820949	t
22cd5734-9234-4009-8349-809a8168d548	edbf0dc9-24d0-471e-ab26-a740cc91bb9c	t
22cd5734-9234-4009-8349-809a8168d548	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	t
22cd5734-9234-4009-8349-809a8168d548	f00a2f72-cf43-4d39-a353-4fdc37d3effa	t
22cd5734-9234-4009-8349-809a8168d548	2d96c814-8a3d-45c1-be46-05be9fc4f34d	t
22cd5734-9234-4009-8349-809a8168d548	b0295366-0ab4-4bb8-9da7-586cfa842c92	t
22cd5734-9234-4009-8349-809a8168d548	b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	f
22cd5734-9234-4009-8349-809a8168d548	19a9cc74-7a24-4000-acea-2952b7b055ca	f
22cd5734-9234-4009-8349-809a8168d548	fe70e32a-bc64-4c88-8533-b86c2a3bdba0	f
22cd5734-9234-4009-8349-809a8168d548	bbc3bdaf-213d-4365-a42c-40040a44d8b0	f
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	43388d0a-5526-4134-8e1d-3319eb820949	t
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	edbf0dc9-24d0-471e-ab26-a740cc91bb9c	t
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	t
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	f00a2f72-cf43-4d39-a353-4fdc37d3effa	t
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	b0295366-0ab4-4bb8-9da7-586cfa842c92	t
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	f
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	19a9cc74-7a24-4000-acea-2952b7b055ca	f
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	fe70e32a-bc64-4c88-8533-b86c2a3bdba0	f
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	bbc3bdaf-213d-4365-a42c-40040a44d8b0	f
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	43388d0a-5526-4134-8e1d-3319eb820949	t
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	edbf0dc9-24d0-471e-ab26-a740cc91bb9c	t
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	t
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	f00a2f72-cf43-4d39-a353-4fdc37d3effa	t
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	2d96c814-8a3d-45c1-be46-05be9fc4f34d	t
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	b0295366-0ab4-4bb8-9da7-586cfa842c92	t
a685d671-6d25-440d-a108-2678b8b9c297	43388d0a-5526-4134-8e1d-3319eb820949	t
a685d671-6d25-440d-a108-2678b8b9c297	edbf0dc9-24d0-471e-ab26-a740cc91bb9c	t
a685d671-6d25-440d-a108-2678b8b9c297	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	t
a685d671-6d25-440d-a108-2678b8b9c297	f00a2f72-cf43-4d39-a353-4fdc37d3effa	t
a685d671-6d25-440d-a108-2678b8b9c297	b0295366-0ab4-4bb8-9da7-586cfa842c92	t
a685d671-6d25-440d-a108-2678b8b9c297	b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	f
a685d671-6d25-440d-a108-2678b8b9c297	19a9cc74-7a24-4000-acea-2952b7b055ca	f
a685d671-6d25-440d-a108-2678b8b9c297	fe70e32a-bc64-4c88-8533-b86c2a3bdba0	f
a685d671-6d25-440d-a108-2678b8b9c297	bbc3bdaf-213d-4365-a42c-40040a44d8b0	f
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	f
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	19a9cc74-7a24-4000-acea-2952b7b055ca	f
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	fe70e32a-bc64-4c88-8533-b86c2a3bdba0	f
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	bbc3bdaf-213d-4365-a42c-40040a44d8b0	f
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	43388d0a-5526-4134-8e1d-3319eb820949	t
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	edbf0dc9-24d0-471e-ab26-a740cc91bb9c	t
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	t
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	f00a2f72-cf43-4d39-a353-4fdc37d3effa	t
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	43388d0a-5526-4134-8e1d-3319eb820949	t
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	edbf0dc9-24d0-471e-ab26-a740cc91bb9c	t
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	t
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	f00a2f72-cf43-4d39-a353-4fdc37d3effa	t
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	b0295366-0ab4-4bb8-9da7-586cfa842c92	t
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	f
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	19a9cc74-7a24-4000-acea-2952b7b055ca	f
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	fe70e32a-bc64-4c88-8533-b86c2a3bdba0	f
6cf5204c-9daa-4ba8-ad21-48a19fbfff09	bbc3bdaf-213d-4365-a42c-40040a44d8b0	f
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	2d96c814-8a3d-45c1-be46-05be9fc4f34d	t
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	b0295366-0ab4-4bb8-9da7-586cfa842c92	t
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	f
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	19a9cc74-7a24-4000-acea-2952b7b055ca	f
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	fe70e32a-bc64-4c88-8533-b86c2a3bdba0	f
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	bbc3bdaf-213d-4365-a42c-40040a44d8b0	f
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	43388d0a-5526-4134-8e1d-3319eb820949	t
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	edbf0dc9-24d0-471e-ab26-a740cc91bb9c	t
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	t
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	f00a2f72-cf43-4d39-a353-4fdc37d3effa	t
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	b0295366-0ab4-4bb8-9da7-586cfa842c92	t
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	f
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	19a9cc74-7a24-4000-acea-2952b7b055ca	f
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	fe70e32a-bc64-4c88-8533-b86c2a3bdba0	f
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	bbc3bdaf-213d-4365-a42c-40040a44d8b0	f
b46397d7-5fed-4d25-be0d-2957ef5534ec	2d96c814-8a3d-45c1-be46-05be9fc4f34d	t
0fce81c2-286b-4aae-9d5b-896ddbe9fd59	2d96c814-8a3d-45c1-be46-05be9fc4f34d	t
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	2d96c814-8a3d-45c1-be46-05be9fc4f34d	t
\.


--
-- Data for Name: client_scope_role_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope_role_mapping (scope_id, role_id) FROM stdin;
c914166f-3fea-4dd1-9f2f-734f44786eae	1be4c1a9-a090-4243-ad3b-3bf460704645
\.


--
-- Data for Name: client_session; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_session (id, client_id, redirect_uri, state, "timestamp", session_id, auth_method, realm_id, auth_user_id, current_action) FROM stdin;
\.


--
-- Data for Name: client_session_auth_status; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_session_auth_status (authenticator, status, client_session) FROM stdin;
\.


--
-- Data for Name: client_session_note; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_session_note (name, value, client_session) FROM stdin;
\.


--
-- Data for Name: client_session_prot_mapper; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_session_prot_mapper (protocol_mapper_id, client_session) FROM stdin;
\.


--
-- Data for Name: client_session_role; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_session_role (role_id, client_session) FROM stdin;
\.


--
-- Data for Name: client_user_session_note; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_user_session_note (name, value, client_session) FROM stdin;
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.component (id, name, parent_id, provider_id, provider_type, realm_id, sub_type) FROM stdin;
c5877a26-71ac-47c7-94e6-de09bb86c0f8	Trusted Hosts	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	anonymous
b2edcec5-9ef9-405f-8082-d9450d4b1085	Consent Required	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	anonymous
c17505e2-391a-435a-bb83-c9f127312a35	Full Scope Disabled	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	anonymous
161963be-42e6-43ab-8377-ede57bb92222	Max Clients Limit	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	anonymous
e72e26bf-b073-4cd1-8c04-e2c9b434dab4	Allowed Protocol Mapper Types	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	anonymous
f31a3c7f-f20b-46a3-b7c1-e5ee05895ee4	Allowed Client Scopes	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	anonymous
71025c13-300a-4343-becf-cb17424be255	Allowed Protocol Mapper Types	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	authenticated
e579d1f5-176a-4eb0-b058-20d538042816	Allowed Client Scopes	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	authenticated
4f43d656-0cff-4a8e-9a42-a75192e47f7f	rsa-generated	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	rsa-generated	org.keycloak.keys.KeyProvider	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N
0ebdde9f-78df-490a-ae95-69cf663bcf26	rsa-enc-generated	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	rsa-enc-generated	org.keycloak.keys.KeyProvider	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N
9e5331c6-32c7-4889-bd58-fa5902a4d145	hmac-generated-hs512	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	hmac-generated	org.keycloak.keys.KeyProvider	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N
1e2b038b-00f6-4e3d-bd99-8f64d52daf18	aes-generated	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	aes-generated	org.keycloak.keys.KeyProvider	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N
337abe13-a832-45bb-bc37-1a1fd4024dd6	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	declarative-user-profile	org.keycloak.userprofile.UserProfileProvider	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N
79c1cdc4-ac3f-4b10-a04a-ff41407a04a6	Full Scope Disabled	866b2156-2043-4bd6-8375-6cff30af44a0	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	anonymous
7b161d46-7a6b-449e-8ed4-4f2dc1dc85d4	Admin	866b2156-2043-4bd6-8375-6cff30af44a0	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	authenticated
e9785c2f-9ed0-449d-bda8-de7929f6baf1	Allowed Protocol Mapper Types	866b2156-2043-4bd6-8375-6cff30af44a0	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	anonymous
56549c55-f6a2-498c-9987-146500fd419f	Allowed Client Scopes	866b2156-2043-4bd6-8375-6cff30af44a0	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	anonymous
5234cffd-0c2a-4e88-9b92-5270492f797d	Allowed Client Scopes	866b2156-2043-4bd6-8375-6cff30af44a0	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	authenticated
4d1177cf-8b5a-4dba-ae89-de4d5869b14b	Consent Required	866b2156-2043-4bd6-8375-6cff30af44a0	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	anonymous
1b927698-2c55-4197-bdef-aa403f446993	Allowed Protocol Mapper Types	866b2156-2043-4bd6-8375-6cff30af44a0	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	authenticated
56cc68db-c3da-4977-8d56-dd2100c6b12f	Max Clients Limit	866b2156-2043-4bd6-8375-6cff30af44a0	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	anonymous
d0c24f71-c1d3-48b8-9b9e-db16aeb44982	Trusted Hosts	866b2156-2043-4bd6-8375-6cff30af44a0	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	anonymous
9ffcfa3a-cba4-4771-8bdc-fa6531671157	hmac-generated-hs512	866b2156-2043-4bd6-8375-6cff30af44a0	hmac-generated	org.keycloak.keys.KeyProvider	866b2156-2043-4bd6-8375-6cff30af44a0	\N
8591679c-a42c-46b8-970c-f6e1ddf1d70b	rsa-enc-generated	866b2156-2043-4bd6-8375-6cff30af44a0	rsa-enc-generated	org.keycloak.keys.KeyProvider	866b2156-2043-4bd6-8375-6cff30af44a0	\N
30a529bb-f7af-4c7e-855c-41b1d961b36f	aes-generated	866b2156-2043-4bd6-8375-6cff30af44a0	aes-generated	org.keycloak.keys.KeyProvider	866b2156-2043-4bd6-8375-6cff30af44a0	\N
6fb6c183-6b00-4d68-a7f7-296faf78eea8	rsa-generated	866b2156-2043-4bd6-8375-6cff30af44a0	rsa-generated	org.keycloak.keys.KeyProvider	866b2156-2043-4bd6-8375-6cff30af44a0	\N
\.


--
-- Data for Name: component_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.component_config (id, component_id, name, value) FROM stdin;
c3e8c277-df64-4e06-ba29-38da791207b4	161963be-42e6-43ab-8377-ede57bb92222	max-clients	200
db200061-c46c-46b4-b396-c590e9f73f34	71025c13-300a-4343-becf-cb17424be255	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
9cef5b9e-d32c-43cc-8700-ba5775215b39	71025c13-300a-4343-becf-cb17424be255	allowed-protocol-mapper-types	saml-role-list-mapper
4b53cba9-5339-49ff-b7b9-1a19e285fc07	71025c13-300a-4343-becf-cb17424be255	allowed-protocol-mapper-types	saml-user-attribute-mapper
aed377f6-bcb6-41fb-91b0-3ebaff7288ad	71025c13-300a-4343-becf-cb17424be255	allowed-protocol-mapper-types	oidc-address-mapper
29dab51d-b624-4167-9ab3-5b8a53d3cdb4	71025c13-300a-4343-becf-cb17424be255	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
f8318a11-4370-462c-9372-ae00f4e227eb	71025c13-300a-4343-becf-cb17424be255	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
0bbee014-119f-4800-b643-455e63d5d705	71025c13-300a-4343-becf-cb17424be255	allowed-protocol-mapper-types	saml-user-property-mapper
39cc2da5-d2e0-4d27-9745-814dff9e8072	71025c13-300a-4343-becf-cb17424be255	allowed-protocol-mapper-types	oidc-full-name-mapper
361754e7-a712-4078-8d7b-0075e5f11d50	e579d1f5-176a-4eb0-b058-20d538042816	allow-default-scopes	true
43de34a8-8255-464c-920b-bb62f8fdeee9	c5877a26-71ac-47c7-94e6-de09bb86c0f8	client-uris-must-match	true
8f018f7e-f371-486c-8401-093908dc3d23	c5877a26-71ac-47c7-94e6-de09bb86c0f8	host-sending-registration-request-must-match	true
ebc2a93e-a0d8-4143-a042-8426c4eedd17	f31a3c7f-f20b-46a3-b7c1-e5ee05895ee4	allow-default-scopes	true
314252d8-ec9f-469c-aff2-515ea7f5c9b9	e72e26bf-b073-4cd1-8c04-e2c9b434dab4	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
dbd0f6b1-66c5-4e6f-98ca-9d4806f69de7	e72e26bf-b073-4cd1-8c04-e2c9b434dab4	allowed-protocol-mapper-types	saml-user-property-mapper
6356f14d-8d5d-4ee4-98d2-bb9d224188a8	e72e26bf-b073-4cd1-8c04-e2c9b434dab4	allowed-protocol-mapper-types	saml-role-list-mapper
1b7ea49e-5f96-4091-9a03-dfb288f43ee2	e72e26bf-b073-4cd1-8c04-e2c9b434dab4	allowed-protocol-mapper-types	saml-user-attribute-mapper
61f9b8b2-5728-4ec3-b3ed-6dea79e527bd	e72e26bf-b073-4cd1-8c04-e2c9b434dab4	allowed-protocol-mapper-types	oidc-full-name-mapper
dc15e5cb-c09c-49f0-9f48-4b974668604f	e72e26bf-b073-4cd1-8c04-e2c9b434dab4	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
e9595d24-9a05-4c1a-a413-a947279b361a	e72e26bf-b073-4cd1-8c04-e2c9b434dab4	allowed-protocol-mapper-types	oidc-address-mapper
035f6802-bc97-4ff8-9418-6e84bf6d0527	e72e26bf-b073-4cd1-8c04-e2c9b434dab4	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
dde5d624-4928-46f5-bf48-282c55653c17	4f43d656-0cff-4a8e-9a42-a75192e47f7f	certificate	MIICmzCCAYMCBgGRIpSg1TANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjQwODA1MTI0NTUxWhcNMzQwODA1MTI0NzMxWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC9dywSZYtsBqZcalKLxcBRKaaPeBkmyFbGHgZlzaJtE30jWEniLCPeE9buuKSAljA6vu8cNuJf8p3GFbvB/T9nVwq2PinRY2QuqSNVDSvhL1cQFlRc2pL/ggMIMYPd9KZ/stwl/j+2191n8T1ok8POGS6QwpaI7Rc88WXGgjsI7AVkOTHFXdHiY8jvMsS9LwNZrBonnQP/nZehDINPXYughxFtUA2E23EGNQt32YJ+T0QKYrFSiSk40sYMpuA8oaYp9v7MA4ngUD9nEvOd+vQ+0q0t+1FL2A3zrguMCD4o8bRVdfF77Gd0z9S2VZ7HRXvPaoFpr+cTw09X7wK8x2hHAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAIeTPbib+9HwA7vAxfRb6uDdZ3v28xsV3HwR66U3xHW/qzVBGXu/RInbHw19ftEsRf6fJMMl+gw8M4fiC+0nx3HlaYJLLCsIxvuqxeiob9Hlj4nNXDRc0OlyUz+kmtX2cKM1ubxMWgvSufmGvf9Rswxy2zD1TfBenrI+OuXVNhFpiwFSwu+noEj2tpVo2JRTVkqsDF/Nleq6BBZkYxuFT/GLcCX+tw+z8biQ9bTYUqKEPfvDDiU2VhJHkf1AGoVHJWvRNo6BSCyX6ZdiNuFLQd2ehcFTTBXTkBNAVhtWY3b1Lem7737d0yv1mIXOTKpiU7GIw+bI0Z5OYYYuXwIoHhU=
451f0cfd-e9fb-4d19-a4e8-a18e720a2bd2	4f43d656-0cff-4a8e-9a42-a75192e47f7f	keyUse	SIG
43881890-1ed1-4ec7-84d2-44719d7a0784	4f43d656-0cff-4a8e-9a42-a75192e47f7f	privateKey	MIIEogIBAAKCAQEAvXcsEmWLbAamXGpSi8XAUSmmj3gZJshWxh4GZc2ibRN9I1hJ4iwj3hPW7rikgJYwOr7vHDbiX/KdxhW7wf0/Z1cKtj4p0WNkLqkjVQ0r4S9XEBZUXNqS/4IDCDGD3fSmf7LcJf4/ttfdZ/E9aJPDzhkukMKWiO0XPPFlxoI7COwFZDkxxV3R4mPI7zLEvS8DWawaJ50D/52XoQyDT12LoIcRbVANhNtxBjULd9mCfk9ECmKxUokpONLGDKbgPKGmKfb+zAOJ4FA/ZxLznfr0PtKtLftRS9gN864LjAg+KPG0VXXxe+xndM/UtlWex0V7z2qBaa/nE8NPV+8CvMdoRwIDAQABAoIBABPfXhYH5JnOEMD8ve+/LC7XKYs+05SHyuI43/7plLHQK337krRQYOq4NgEQCOEb368pu7a/4vqhXf8a5HiycWYLcSowVv6JaEakovoWyZVy+7TKQssCegymvF6touEy+gvG3OR3SIOLHMP8P66qYQ1LAuFYJ6XxCFDyGPS7UCndOW6ntTWQaVaZqOuF4tBJPWsteK5fYbaeLxO9AiuLTv7Ey8lgvSYfQyjMdmPXycrGiVuBsahTIFxZCQKe+H84xa1W8Iwat6MgmVFoIgf+ZFqpVda5zZRhQ8yyHpzglBvG6F+0+Q8Q8PfcO9qce5Y4YZ6gtSR1rjaMF9dV2p7MerECgYEA5Uv59td6Y/iURyx04BsdhzDzNLu2K8c+Q4pXxpZJ3mAle1vbvGi4QAiZOaoNiEI269HNXbLIS87+iksUhp7d+AQldTSbb8vXBp+95sq2EFjnNHPrLJ/y8ax9oniM25BscmTVvd2KQxLTJ/S2IO3F6vgIZmEw/eRDIfIyL2YmuP0CgYEA04e02EQBaaFNB5BJUFR0Ve723yuxzyIbCdTb/lKITEVhHl3a3G/EZorE4+Rb0Z7GCQsb8xe3mnjlr71mvEzA5BHVfUNEgiOozNoMTPNuJCNhXEWViCy4VtLZSloMSx7GzCiWV16NCxpdETQEaNGtwGYU3+ybtgHBokiNKF/Sm5MCgYAJAXjp1wC5mHPKb9aPTsFUI/oJYi3/fi3V3UMhS5nPIBuYwrzYo4xJB41tFF6/sUoS7QSR34m6YIDi0ZwWxXqWWSNXfR91vp3NZszAmcmYzsUV7E+aSxXxpAKzNOTjYsK/o3I5qM/bXovoj9XvaSdS2TcLPln8PtDcLnUGI471yQKBgGY5A60rmB8q4yXPlY5rcdkBZW5OGFyIF9b1deb+jX2GXbumEZlLrfb8AtA60CCKARvnsXcVtBXDbQQznI1M7j6JW8lwE5BsmSt+CEm7rjNqoadMvUmUivTyai2jPKOk2N9akeTg/XorIGSq5aKeCcCb1GSuXEVlreS1+PlU7VtfAoGAXhSJEZOYihQdhSQSQBiqcQRB1dP4SIEuZ+5HiWGTOr3rJe3UgjG9/ywWweVJwINv2xr0HM9AXPfb4MMbC7uAq3saPhh1svcHHr0uHA4YEIXLDtIdmHFOQOywWEbskABCmLR1ogJm6omHawwrS9xf1D5H3S88KVKMSaxfjEKsyO8=
3ddb21d5-5f12-43be-bdba-ac5547c98ebf	4f43d656-0cff-4a8e-9a42-a75192e47f7f	priority	100
807e5de6-33e0-4008-8845-7f05e1b7af80	9e5331c6-32c7-4889-bd58-fa5902a4d145	algorithm	HS512
b07b865a-d14e-442b-9299-17b82a076db1	9e5331c6-32c7-4889-bd58-fa5902a4d145	kid	69dddfb5-0842-4ed9-bf59-24b721b81350
7ec3fdc5-c5b3-4a90-8df5-00dcea755444	9e5331c6-32c7-4889-bd58-fa5902a4d145	secret	FfJMYEuqnamRQe3S-2Rg5LkCF0oR37VVTwm5dftfeb7VPf5TwpTLJPxa-7QMQ8oAsc_Gke5U-D9cMPh-XLlw4OneTi3nmEW_SVWP3QPRuBl4O4-E3ilqhyPNAT2oaVOEBivEKg3fHtLgM0X8pc1Hpt-F_1VvRF83XTw7DMxuPAQ
0f2f3f33-71d6-440b-904b-a74bade87efe	9e5331c6-32c7-4889-bd58-fa5902a4d145	priority	100
7b4193df-de66-4ed0-b1a5-9070789ad583	0ebdde9f-78df-490a-ae95-69cf663bcf26	algorithm	RSA-OAEP
f1ff4256-f4c1-43e5-bfea-f1612a85e48d	0ebdde9f-78df-490a-ae95-69cf663bcf26	keyUse	ENC
890c9e31-aef8-4d3e-8d1c-2484c5e48488	0ebdde9f-78df-490a-ae95-69cf663bcf26	certificate	MIICmzCCAYMCBgGRIpSiijANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjQwODA1MTI0NTUxWhcNMzQwODA1MTI0NzMxWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDKB5s8cSFO/NCC0urvAJ/lZxbYLatxozrBxVBBLLqdoWJxVNapTpdeOayD+17OqWmeMM+QLJcQhd3Kcs5AKMPOveJZXGMmNeMvSb2gvURVPAp8c8/9t0wVXtuF+velJ/ApuBEm9ophmEdcTOClJWJ6PgT3fSki3qbd9M6OVvP46qD16bZlydTcdJCZuVyqZwosSWnS9lN34s4YG/bUNTe3iG9kvdOwaJ8Q3nuP0kYb0Hy0RliWFdxhnY2YP8fy7U8Pd+400qBkodi956Ao6xykrSHF0vaGbbDxHwvLR0s3DPFtfMCoAGH7CpWuNYxruGMzhjOq/T0e+z4pcnXQDqgFAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAI+EQW+w2NOleRM19OgAXkdSQLaZTYudnJtKwAtJXy7eXCEwFdwr73GJLPJ8P7NSgaFTKZrdRC7rNCu4hjk3BSuufCpecsZ7Lzu21skdExDGPrloTzE0sOpMiZhXTBUjHlJ67L2zJhV6kArU00WjQPq0PhtpAWXIftrPvPxkgvXyJuhrIf1S2p5zlZwA9D2DnvibAJX7PqT1zHnO2vF7uF1ggUEdOhkGP27qtVJscOKUJ/5GvwqzQ+a3hBfmXPn9OzQaQqdnc/3mINWQE7kumvK0rw5gwojgsgj4beJDK/NpTQ9yMRlk5FJE+0aWcGUC6XcYVvwy/DWaC4wZd0OBo+E=
e47e9907-47fe-4ed8-b111-cf0c19fdf920	0ebdde9f-78df-490a-ae95-69cf663bcf26	priority	100
1b3053a8-11af-4bbd-96e8-99aa949bab2e	0ebdde9f-78df-490a-ae95-69cf663bcf26	privateKey	MIIEowIBAAKCAQEAygebPHEhTvzQgtLq7wCf5WcW2C2rcaM6wcVQQSy6naFicVTWqU6XXjmsg/tezqlpnjDPkCyXEIXdynLOQCjDzr3iWVxjJjXjL0m9oL1EVTwKfHPP/bdMFV7bhfr3pSfwKbgRJvaKYZhHXEzgpSViej4E930pIt6m3fTOjlbz+Oqg9em2ZcnU3HSQmblcqmcKLElp0vZTd+LOGBv21DU3t4hvZL3TsGifEN57j9JGG9B8tEZYlhXcYZ2NmD/H8u1PD3fuNNKgZKHYveegKOscpK0hxdL2hm2w8R8Ly0dLNwzxbXzAqABh+wqVrjWMa7hjM4Yzqv09Hvs+KXJ10A6oBQIDAQABAoIBAC3ulMilH+GJcQ6NTYSRK8lIKtVzbKslZVJwvdJTjQoRiwQhOby8dYWirypCmEd8FNs2Vbp9qMrE5KwGdgX4PqxeV3vhmVkynzg9F7PX1BVc1TV+Tydr6QxiyJ4pdOxDrEKP6rZlDwkpSfAeOmTRnxYTy6VI+3VGEcIcymlqM4W/yr8VB2dm59ZrOa9MFUAaygvfBMau1hyiHbLra/9SB/aFbCtYVElnUfvRZTheiUtWuH+BCOr3/7LYx3BStDreMJ6Y9kcvrFMtRc8vPM3nZdeqGs150YsUZPj21ByMmi/sIXIRyIyiIhZx7w1nYkkPIIiLYUQkL8YjkaLIcTx3ToECgYEA8sUAHxkz9Ql3auadO0Jy+zhuWJdCtyfUWPqgqamENYmxkI+3qlIV4+NU1WF4jIwelwIvzG3QSnpE/U5NzRlKBs1/7lYn4DrIkVEQvwpcn4qhtf5yyts4igMUuhypjWuhMBryaYY9b1mxYS9zxda6Fz7jIvl/Exj3HJ8uxCqCQw0CgYEA1Qo5aBPqoAZXp/VmI0InbXjjgmTOQx3zwI6m9dbP8m58UEicVOkEQgfjHG0ZtqSErdpVoS/ibEMndG+1Au7DdEv5s9uKdVeF97JNXHX4mqtmBtRJBvRMLIq8gQtQT2ncTY1QOQ6/U8wYTZdRjauUXUeodp7X65TWbtCr8v0GmtkCgYBntLgkkMOdPXoB9JneIrizkJuyxIr/ashPamEPmRadOEDeHEa9P2uSeh5XONgiUgkPQSPiFFcSOcEW5EhgARmaZlbsHWnTY8kiKpsYwPhoPvRtplCrT5KLgl1sU6oqe3vT7O75HRtJYetgNMEkFmJQY9Drs+EQ621EkMuNiQs0UQKBgBIzWGoy4WWghRadWYeDnFi27kDymciUI4H87fiszIE6vTYirfB3I9z8TYPdMkgvUAKGV7B4pdB4AEPrUzyX52zlHMUn6XFS8+gQfJl8nqugHwxQUJjegC+B/s7Lh6XxNlgNzZ/CScg/9IeUeOwwj3FUsOjJc0r2IUfAXiBZS5opAoGBAPC1hK1mxHqyNc8p+RxM175xJide1Wd7PljCu1RIkmjK8Lcdr4bYrjfWhrTLhL1+w+gige5AtfMTP3RIVEtiq5NGBtbaPlspfxn0yoc2nA7+lTu0LCK4JpFFkKCqcHnVlVOKUF895bnwV569Mw5t7Tj7u/4o++CS1+rcXe/SwIrv
a7ec2a1d-ccc4-43d9-81f6-765560c5760b	1e2b038b-00f6-4e3d-bd99-8f64d52daf18	priority	100
f70461fe-bd5d-4e43-b587-7cdc60cf20d0	1e2b038b-00f6-4e3d-bd99-8f64d52daf18	kid	4c17bf03-9003-4688-ad4e-bf764366a13b
2bf8ddab-9182-4ec2-ab2c-4cd44daffce6	1e2b038b-00f6-4e3d-bd99-8f64d52daf18	secret	lHexHo79qjD0-1_8c9dP6g
9c47b76c-8c17-47b1-8677-afa2fb9e06cb	337abe13-a832-45bb-bc37-1a1fd4024dd6	kc.user.profile.config	{"attributes":[{"name":"username","displayName":"${username}","validations":{"length":{"min":3,"max":255},"username-prohibited-characters":{},"up-username-not-idn-homograph":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"email","displayName":"${email}","validations":{"email":{},"length":{"max":255}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"firstName","displayName":"${firstName}","validations":{"length":{"max":255},"person-name-prohibited-characters":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"lastName","displayName":"${lastName}","validations":{"length":{"max":255},"person-name-prohibited-characters":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false}],"groups":[{"name":"user-metadata","displayHeader":"User metadata","displayDescription":"Attributes, which refer to user metadata"}]}
68e98b80-679b-44a8-88ef-effefbc6e7fa	e9785c2f-9ed0-449d-bda8-de7929f6baf1	allowed-protocol-mapper-types	oidc-full-name-mapper
b331b3ab-aa05-48b1-922d-c9c82cbbf2d7	e9785c2f-9ed0-449d-bda8-de7929f6baf1	allowed-protocol-mapper-types	saml-user-property-mapper
6137dc38-3d7f-4ffe-9fee-4c2166323a4b	e9785c2f-9ed0-449d-bda8-de7929f6baf1	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
92505c30-4617-4b45-b048-7d6810a98e64	e9785c2f-9ed0-449d-bda8-de7929f6baf1	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
33c6a283-df8c-4008-9ebe-dfd04fb932e9	e9785c2f-9ed0-449d-bda8-de7929f6baf1	allowed-protocol-mapper-types	saml-role-list-mapper
f3703504-f354-4994-af43-b51ef9cf41e5	e9785c2f-9ed0-449d-bda8-de7929f6baf1	allowed-protocol-mapper-types	saml-user-attribute-mapper
1751afdc-79b8-46e8-9b2a-977895417722	e9785c2f-9ed0-449d-bda8-de7929f6baf1	allowed-protocol-mapper-types	oidc-address-mapper
ff68be1b-abfc-4e63-83e7-2287908c0907	e9785c2f-9ed0-449d-bda8-de7929f6baf1	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
ada42d35-9d43-4542-b11d-9e0d3361d80e	56549c55-f6a2-498c-9987-146500fd419f	allow-default-scopes	true
dc51125a-341d-40f8-b670-e7ae7c874554	9ffcfa3a-cba4-4771-8bdc-fa6531671157	priority	100
741ac583-e816-49f5-b088-6b6e95aacf6a	9ffcfa3a-cba4-4771-8bdc-fa6531671157	algorithm	HS512
e38dea65-1b4f-45e5-8bdd-e5e74201c529	9ffcfa3a-cba4-4771-8bdc-fa6531671157	secret	BZijGr1-OCXeHROFVjn-S-1ZUvLAFdPVfyBCfhk0M5Penb8JKMKbmzOYKuryFp3_f_5TgZkDFu22fxJTHtcx3-UiOpq0Lj7cqK-F9Rkx4niFFOcyxA2kNt_uRWZNgSp-hReB7SmiXZsbxV2uygGg4APFYXtyXh1_fhcgJ6JnmcE
bfee4fa7-62ff-4d10-8252-46f95ec328f5	9ffcfa3a-cba4-4771-8bdc-fa6531671157	kid	b3dfb4f4-8e09-4d39-87b0-63d017b2baf1
bec30ad7-6fbc-4aeb-b08e-237670f061c0	8591679c-a42c-46b8-970c-f6e1ddf1d70b	privateKey	MIIEowIBAAKCAQEAy8FZys+O0FnQZXHBl0NWrNq6OBpTr2VqvcpLCtk6obUlrt3TUihGheM+osBbIGozxW+knUK1zHwSTGRO4kePO/lLyOdd/bkqgasetAZdP5H5+ZdfJ/DDM6W43SakHrvswGGqJHLtFYy2ZYTRTE2hhnBO8ByZCImATZyoAiEAKIElYY5KyMkoTd9N5G7kDfj9flwIkofe4JwE7hE9AmHneOfhOl2tfMk19iRi6GKd1sZMvXZq3hMkYOUPSVsGqoT6ZECEnGyK6tN1GTPrtGywb+JUlLEcTIkJzNXaCUE09BluhM2Z9PwYAXzZIy+DN89SSK4hYmiUkQL8Z/apNBTitQIDAQABAoIBAAJqOH75nkr0YY6uPjnle6vBTOc40qiXxl/Y+7I1eGctvzRXHzfQT+Oq2ZVyu7HC6Hms1NHjVkXF666ZmsC+UeGHVyFasqe879bJGSxPjf/gokqVw3Q0+49LZclDObs+hXQTREG51fsFJ61E2Xkyy1kNUDYy1JKAFLP2yRv7WaXhOomZShD1CghjMyX13zqEhtLH/pMFJ+DHb+Qjck3sYTTp8YprFZiGogwIhrPAmL2V1C9mBy3OEX4FlxQ11r8lZ8vdBfPtIHrvsqiipwYxR39W9t/FWOgdlrqOIaGG0wKxz6oQENSUXoToB0kTlWNSgZjm2Cbx9+TUyYZDZBfj1EECgYEA+XU4Fl1gmFuXm65p9YVYSmBKq+mKPxaUFa6nSSimS9JXh4ns8b5Lr2qDSS4ZiNFzLnP2eXhUJniCZ2Y65q97UWhMy5MSyBoUoN2vK1blh4kRgCdV8H5jSOkIMwN4gol7V5BsxLCHkYnip/YNnpDqSvKa8pWBzqCKXguizxyH9cECgYEA0RlMgpz5qZhkr+79Cr+/fDk8Vk+ZYi30RL+XSdS6fnf2H2Y+d9CKr0akEk2CLxR2YGABcRTq+Zfw6wWtCKr7gdcuXa10UqfRqoNtywucMLCc2cmlwxgOkcuLBCim0gy/GBE5Els2cxiV7T+hyAJ4S1eTyHQKVpugoKas4qac8fUCgYBVXYqrBJtYqAdSrDXwYghbs2X6PHolqrlUTidK/tEho0J2zHk8JM3Lopx4v4DXdC2BWQege8/4NkcR0UcYyDZgHlwhUhwcQ+iJQDOAlkUHw50siMl8+hNJ0Q0QQADOUvPVfEPsbyiFYnUg2y+/8oFq0mi52PXJ2uBe3IaDb5ULQQKBgHJoFza30Np8aUrhxk3lUE4Vuu+2qcPQRQCAm7Rn0opz9JSFitRfpCH20rq48ev0lDhzDxbiTRa3jbfp6Rz/AJGwPMxApY68B7n2I1KfTZxOZeO7sxilxwL6jVt/KpdvJfdzJPjoM0iSQSYIVOpsf4kpp9BbvXRZvm9oAtwYMXMpAoGBAL4r8EP0CDBz5LakM6lQB5B2GQ59fyQnCkWExb6qP7KWKFjodaO7GYJrgbnabpqbz114Ua+ySzuuvTeKrCGgj7qyG5JLVLRwXExcqbnnuPM5re9Dun/7wcYak8DwnlroXfe+2Cb8YoVI4Mk2emMHa55pEF6b1RjiPrKCsUblcFS/
634b5fdd-9c63-47ba-9d4a-847a07664a5b	8591679c-a42c-46b8-970c-f6e1ddf1d70b	algorithm	RSA-OAEP
6290807e-58d1-4bc9-895d-f74138033754	8591679c-a42c-46b8-970c-f6e1ddf1d70b	certificate	MIICqTCCAZECBgGRIpTHCTANBgkqhkiG9w0BAQsFADAYMRYwFAYDVQQDDA1jb250ZW50LW1ha2VyMB4XDTI0MDgwNTEyNDYwMVoXDTM0MDgwNTEyNDc0MVowGDEWMBQGA1UEAwwNY29udGVudC1tYWtlcjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMvBWcrPjtBZ0GVxwZdDVqzaujgaU69lar3KSwrZOqG1Ja7d01IoRoXjPqLAWyBqM8VvpJ1Ctcx8EkxkTuJHjzv5S8jnXf25KoGrHrQGXT+R+fmXXyfwwzOluN0mpB677MBhqiRy7RWMtmWE0UxNoYZwTvAcmQiJgE2cqAIhACiBJWGOSsjJKE3fTeRu5A34/X5cCJKH3uCcBO4RPQJh53jn4TpdrXzJNfYkYuhindbGTL12at4TJGDlD0lbBqqE+mRAhJxsiurTdRkz67RssG/iVJSxHEyJCczV2glBNPQZboTNmfT8GAF82SMvgzfPUkiuIWJolJEC/Gf2qTQU4rUCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEATrIHL2La6iDO7ljNYhq5B8LwrzNCZPZrm5inxIenWNx9JuuTjsvFG2YXb6UkBiGTn2Zy1Ob/FhTZBWR4R2cpy5GSIyaaAs9Ljmq11ULmTFI7jo7tLVQLaIKzJPjEwzo3wpiHbyC+jSTHoAjg1kYBAfash3rJIK18+5t8bSZAYXU8dPOo5SmOuFuZoJBYFgtencEZNs9CmEPkdUBud3wBo9pDs0k0870qAQK5RGHiCchSK0GWHDgppQyRZpBPRajGtbRW6vyzBxz/TLeD/9gj1STRopgkJRMGaL8FWP8XvWaIbBV99nFLYBM2Rf1knk1iZIN1AZZ51iDyEbYfezFO9Q==
98fcf746-ca17-48ef-b7c8-ff8ac1f7937e	8591679c-a42c-46b8-970c-f6e1ddf1d70b	priority	100
490c9b97-69b6-4d23-97a0-b396f0444092	30a529bb-f7af-4c7e-855c-41b1d961b36f	priority	100
4f094e73-9a16-4abb-af95-2dad01b01885	30a529bb-f7af-4c7e-855c-41b1d961b36f	kid	33ef95e5-882c-48b8-876a-ad72c606ac37
00e6f00a-778b-44a7-9a21-dfc378e1dfcd	30a529bb-f7af-4c7e-855c-41b1d961b36f	secret	RcrVYzHsQHZgbJTGk31QVw
5f0490ee-a64a-488c-9a61-e4d5b45925ae	5234cffd-0c2a-4e88-9b92-5270492f797d	allow-default-scopes	true
a9c42db3-9e38-496d-8faa-cb872dd0c607	6fb6c183-6b00-4d68-a7f7-296faf78eea8	privateKey	MIIEogIBAAKCAQEAyi0OoXTKih938pUB9WmSDbJOGa6fFH2C7ddCd1/sjvFhUBN1MOVSCd51trux4lZWl+JZKA99OYBy3HnFfrTbHJvx19p4bX+ZwiyU8n53F4avB74htJcb1rLeWTZ340sD0azQzytMTqmG7kfAHcdmIRFURokYCWF6Dd5UMKJPGKDhrpkhCpOGKfAGMsHFmS8d7b9OTw7hqVtvFs3WIVI9IQNZKxxHDdbQ+4h6/A8G9egKnW9tqFbQ3Hj9I9C0hD0qqf4/Q4E3zAjwlNYwOwN/dw+4KcqK0wpF9ADr2fm9vmSxkD/dOG6aN0TMobMTybs5FM2xk+bgeKEbxkrqLj56lwIDAQABAoIBAEUahnwwP8RbQjPjs5Iu/fF5hxFnKs+loNlrsLe9zU0EezcUF4PZdiOSapjFaeVLjniGsOOs+6fqXBg49QS8i4IpWU3myi/fI+6HL3f9OvaLHgte9QVVevn1FGPY5ryFWO5Ti+Gr0n62mjyawLNNAv79fdlIJbObIk1YN12SNMfdJd0u2xbb8FkHGFsr7RNcsCZ1C8V9OXW2LgguYenS+EbEJT+sTlVcwmzr2xdadro9U7Nn9wtoYw/xxcW47OkK/P41P7xBAYdNJZ/l4S+VxHtLc2A3RDanSMBvngoQylwR4NL1X6mlAiTRC/BCbOTKO+cwgy55NWIb5e5F7lL8kCUCgYEA72GwrCSzPa0/KfSC0IdgkSJkj1nKS3JxpQACYyUHCnctZlFXSkO/qpwBel8ychpGL4jcy439/meCDwD60TN90+iCgnUBz86mxsutHYSBTACo5o8xoEI1McLWc+1dvYlQ1JsNCX34Y2fiJBKR1ThpQDaYVMGRautNtndWDJeSZZsCgYEA2DYlYjLir7sZ/xc7bciYEjCRLp437jUicUf7sVYHvENr+UOW38bWh+lpmOGYvIGTlR6H2eW8zB8vfgBh8MFnOG8plHtWNx/fQif5tR2L/RX7UPzbOtnAMGIZBIfL4uUyV/lcpclMqzt70KEFvwIPIC6qLbhQE/2YJeV96yYyLLUCgYBCW2s1HOtO4pKK18vXcF+Ve9oyXqqUJYJZQdpnj2EXRpggL9YpsQQVkC9cs7zxIlVoYeEK0C6jch/8dsIQcdykfGSJM1QwPdG/c8BmWU/aOKiZWQJhsVi7lY4LDraYjtMITH7drbzIGYj3hOpGF+AdO8UYYZwk7ltZhFUnInEjlwKBgEzKDkTuJaycGwOKUMUQ9VGghi+0adDkdjMdibrV/zFSEMsawj7/87mZoLN3jDOHIc9YZI9zFLa6bLenkyFazzov4OinVVuGqlhosjstH0bnw7Zj18jzBVspvBDr2fHfzR2JpuoKsHwwSC00w0GvjYe0iyRpzacL7jmnZC1cB33xAoGAUTDUYSUqQX87SACB3AXtZ1WDmavEYiysJUCSP3VeO8mtmlQUyxRvI+1QZs0OgQag3jd1WsdQeJ8ajDmz2wsjqXFFZusilKOMRN3Y/qcQ8J5MrOlSKTKQVgo1QQ1W9uIt2064ubrvdJa3nMU0CNTyInm18vMhA4Xu67sI3ZtvEzA=
06222f35-d383-4876-8c4a-26a6fd701a82	6fb6c183-6b00-4d68-a7f7-296faf78eea8	certificate	MIICqTCCAZECBgGRIpTIrzANBgkqhkiG9w0BAQsFADAYMRYwFAYDVQQDDA1jb250ZW50LW1ha2VyMB4XDTI0MDgwNTEyNDYwMVoXDTM0MDgwNTEyNDc0MVowGDEWMBQGA1UEAwwNY29udGVudC1tYWtlcjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMotDqF0yoofd/KVAfVpkg2yThmunxR9gu3XQndf7I7xYVATdTDlUgnedba7seJWVpfiWSgPfTmActx5xX602xyb8dfaeG1/mcIslPJ+dxeGrwe+IbSXG9ay3lk2d+NLA9Gs0M8rTE6phu5HwB3HZiERVEaJGAlheg3eVDCiTxig4a6ZIQqThinwBjLBxZkvHe2/Tk8O4albbxbN1iFSPSEDWSscRw3W0PuIevwPBvXoCp1vbahW0Nx4/SPQtIQ9Kqn+P0OBN8wI8JTWMDsDf3cPuCnKitMKRfQA69n5vb5ksZA/3ThumjdEzKGzE8m7ORTNsZPm4HihG8ZK6i4+epcCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAiAQ2BYOWlXjkHcnNSNYqD2tTm74TzDxao6/+1DU6AEdn0Il2VVkOmUbDnvbM3I3UhjYMqWNUCOIDiDjXLVuERBowNJeglReOcz5sHR1PhgB/xBFq0K/FpLmTTP73pe9svTgV4rQ5rCO8943vtVhQ4K/eH45b/o/KMKWtZmndjLcqBOwZGYau7xfK67EtANwxijr+61mxXiUplAhw0IFh69kdZVw6jM6WJZ/gX+fJIbA+WYQ8+1XiqoAX0TS7463pnmrBJ9q5sHCGVjCBIJ/oENo3YdNhwxfUIxno2hD2/0wEtiJxL4jL8jbHjpxDRFUaaXpeV5eTPilkSWG48Bn8Ow==
cd1a1147-4bd7-4ee0-819e-d664628e719a	6fb6c183-6b00-4d68-a7f7-296faf78eea8	priority	100
7433481d-345a-42a4-8bb8-5ff8cbd7e2bd	1b927698-2c55-4197-bdef-aa403f446993	allowed-protocol-mapper-types	oidc-full-name-mapper
c64a71c1-c475-44ef-8058-218c63742ceb	1b927698-2c55-4197-bdef-aa403f446993	allowed-protocol-mapper-types	saml-user-attribute-mapper
7fe8449e-4904-4e7e-afed-4fa278793973	1b927698-2c55-4197-bdef-aa403f446993	allowed-protocol-mapper-types	saml-user-property-mapper
b8734833-89c1-4ad6-9e87-59351d4e94c5	1b927698-2c55-4197-bdef-aa403f446993	allowed-protocol-mapper-types	oidc-address-mapper
24ddae48-35e4-4072-b909-1a587dc75b71	1b927698-2c55-4197-bdef-aa403f446993	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
f24407ce-7923-4de2-bbaa-ba894e334442	1b927698-2c55-4197-bdef-aa403f446993	allowed-protocol-mapper-types	saml-role-list-mapper
b3dbfee2-4a97-4718-8da1-d32509049ae4	1b927698-2c55-4197-bdef-aa403f446993	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
bf2432dd-c08d-4ac5-9e89-8fb7472aed87	1b927698-2c55-4197-bdef-aa403f446993	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
6d750dd9-b0d0-455f-b709-03cc29c47b70	56cc68db-c3da-4977-8d56-dd2100c6b12f	max-clients	200
483693d6-6814-47a6-846a-9c95902db786	d0c24f71-c1d3-48b8-9b9e-db16aeb44982	host-sending-registration-request-must-match	true
b9ee7e48-d1fd-4c25-88db-e75bbdb1705e	d0c24f71-c1d3-48b8-9b9e-db16aeb44982	client-uris-must-match	true
\.


--
-- Data for Name: composite_role; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.composite_role (composite, child_role) FROM stdin;
119defff-e507-42b8-8a38-ff9a18f61179	8ffe4731-e9b7-423d-bb37-aa04aa1e5be1
119defff-e507-42b8-8a38-ff9a18f61179	2e739d13-1803-40a7-8c9f-bbf5d931d5e2
119defff-e507-42b8-8a38-ff9a18f61179	1fcf8f74-d007-42e0-872c-065a3e591182
119defff-e507-42b8-8a38-ff9a18f61179	574f2784-b0f7-443a-ac89-2434ca2b4f4e
119defff-e507-42b8-8a38-ff9a18f61179	eaaa9326-d485-4299-875f-04464511be38
119defff-e507-42b8-8a38-ff9a18f61179	0d0177e7-c758-4e96-92f4-82358b4d992a
119defff-e507-42b8-8a38-ff9a18f61179	5bfb4ad7-562f-4848-988b-dd0c6e9ba472
119defff-e507-42b8-8a38-ff9a18f61179	a97e3e8f-ae70-4c95-a3a8-c5d8031cf56d
119defff-e507-42b8-8a38-ff9a18f61179	b6e6dd96-b2c6-46fe-a0a6-057f65ba9f3b
119defff-e507-42b8-8a38-ff9a18f61179	bae08630-d284-469e-b548-5cae3ac7283f
119defff-e507-42b8-8a38-ff9a18f61179	8fb5cbe7-1876-422f-a6e1-042987f991ad
119defff-e507-42b8-8a38-ff9a18f61179	acdc6497-ea9c-408e-b7c7-b22e65c810b2
119defff-e507-42b8-8a38-ff9a18f61179	b16f786e-5aad-4803-b82c-59e3de53fd58
119defff-e507-42b8-8a38-ff9a18f61179	64d99976-a8f7-4a4c-9add-f8f0305a1c64
119defff-e507-42b8-8a38-ff9a18f61179	e48ac78f-6ac5-4f13-b186-7264ac86d8df
119defff-e507-42b8-8a38-ff9a18f61179	cccb616d-b21d-41e4-b41f-a800baa94803
119defff-e507-42b8-8a38-ff9a18f61179	93dd3cdb-1e6d-49ff-8cf6-c2144a9c24de
119defff-e507-42b8-8a38-ff9a18f61179	b1f8e283-d60a-47e2-8b5c-d56463d6c473
574f2784-b0f7-443a-ac89-2434ca2b4f4e	e48ac78f-6ac5-4f13-b186-7264ac86d8df
574f2784-b0f7-443a-ac89-2434ca2b4f4e	b1f8e283-d60a-47e2-8b5c-d56463d6c473
b0474fc5-0f15-4f4f-b0be-55b37a4f64bc	b535b1c7-09ce-405d-84c5-008eb0f490a7
eaaa9326-d485-4299-875f-04464511be38	cccb616d-b21d-41e4-b41f-a800baa94803
b0474fc5-0f15-4f4f-b0be-55b37a4f64bc	1eb07f91-217b-4e84-9181-51fe8fb73641
1eb07f91-217b-4e84-9181-51fe8fb73641	2c54c3e4-a247-457c-9d08-6abf24b412d3
cd5772ec-0c02-4d34-97d7-ea268c1d0f39	141c8449-c970-4b6a-bd89-03f3c21f43fd
119defff-e507-42b8-8a38-ff9a18f61179	9e9c0a9e-7af6-47dd-9b5e-c5964ec35542
b0474fc5-0f15-4f4f-b0be-55b37a4f64bc	1be4c1a9-a090-4243-ad3b-3bf460704645
b0474fc5-0f15-4f4f-b0be-55b37a4f64bc	a6c508d9-63ef-40f3-991d-8fd703e2d6f4
119defff-e507-42b8-8a38-ff9a18f61179	62a2688e-e798-41b6-b400-f8eef9a15e61
119defff-e507-42b8-8a38-ff9a18f61179	98271fb0-6880-45e9-b84a-5b89a67f5eef
119defff-e507-42b8-8a38-ff9a18f61179	4102c133-7ed5-45ab-bdfc-99c1fc3c3f1a
119defff-e507-42b8-8a38-ff9a18f61179	635343c5-d32e-440c-8650-c7371a4411d0
119defff-e507-42b8-8a38-ff9a18f61179	b096ddff-f77f-46b9-b20d-5cfebda40798
119defff-e507-42b8-8a38-ff9a18f61179	cdee5726-97f6-428e-9b57-cbb1a0092abc
119defff-e507-42b8-8a38-ff9a18f61179	e4226c02-88df-4bd5-8247-659b6e81752b
119defff-e507-42b8-8a38-ff9a18f61179	3d802def-bc0e-479f-877f-1bd500ea0304
119defff-e507-42b8-8a38-ff9a18f61179	a20c460e-e695-474e-af24-8febbea37ecb
119defff-e507-42b8-8a38-ff9a18f61179	aac46f5a-35e0-496d-970f-64efb6647a96
119defff-e507-42b8-8a38-ff9a18f61179	eda8ec5a-8302-462c-9959-62fe66d2526d
119defff-e507-42b8-8a38-ff9a18f61179	e84d24a4-3673-4791-8a1b-0d3892051ab1
119defff-e507-42b8-8a38-ff9a18f61179	e14f7e7e-d05d-4065-871f-10a6fbb42754
119defff-e507-42b8-8a38-ff9a18f61179	7e676520-9822-418e-be62-1b9e2e9ad6a6
119defff-e507-42b8-8a38-ff9a18f61179	ba94f1c0-e78e-44fa-be1b-22a60a81f2de
119defff-e507-42b8-8a38-ff9a18f61179	eec153b1-07fa-4882-9984-aefaf7262572
119defff-e507-42b8-8a38-ff9a18f61179	03e8efd7-e17c-46ba-aed7-52cea4aa1e41
4102c133-7ed5-45ab-bdfc-99c1fc3c3f1a	7e676520-9822-418e-be62-1b9e2e9ad6a6
4102c133-7ed5-45ab-bdfc-99c1fc3c3f1a	03e8efd7-e17c-46ba-aed7-52cea4aa1e41
635343c5-d32e-440c-8650-c7371a4411d0	ba94f1c0-e78e-44fa-be1b-22a60a81f2de
119defff-e507-42b8-8a38-ff9a18f61179	3488f2ca-1dbc-40db-9674-a41edb5245cb
2597a4ca-aa75-434b-a94f-d2061fc608e4	c1ff597a-7b25-4590-ada0-39cbe23c1d41
2597a4ca-aa75-434b-a94f-d2061fc608e4	fff1221c-a306-4554-a821-8e5ce0cbcdb7
2597a4ca-aa75-434b-a94f-d2061fc608e4	c834e0c4-f2f4-4dbc-8763-39c069195f6a
2597a4ca-aa75-434b-a94f-d2061fc608e4	d4fbf3f4-3961-4f18-899a-b186f66b7ec0
2597a4ca-aa75-434b-a94f-d2061fc608e4	ee4e4b4d-18f3-4b29-a943-d00efa62c3ef
2597a4ca-aa75-434b-a94f-d2061fc608e4	4267d9e9-1df6-41a5-b7dc-0e4a7d5a6a38
2597a4ca-aa75-434b-a94f-d2061fc608e4	ee48222f-cc73-4045-ad6e-d72491cfcecd
2597a4ca-aa75-434b-a94f-d2061fc608e4	6e726a93-c9af-4ddb-8a74-fe7f7908f9d9
2597a4ca-aa75-434b-a94f-d2061fc608e4	640c6d85-9e75-4bcc-9888-12fce69bb801
2597a4ca-aa75-434b-a94f-d2061fc608e4	c71fe027-139d-497a-9ff3-74f97c2f296f
2597a4ca-aa75-434b-a94f-d2061fc608e4	d1894fe9-2776-4281-8524-3c0af7b2e16b
2597a4ca-aa75-434b-a94f-d2061fc608e4	6f75863b-c52b-417f-b8af-27abcee077a2
2597a4ca-aa75-434b-a94f-d2061fc608e4	7ea041ef-ed69-41b7-a42c-6fc9a9b1b314
2597a4ca-aa75-434b-a94f-d2061fc608e4	389c1215-255e-4b52-a7f4-28012e5a45ee
2597a4ca-aa75-434b-a94f-d2061fc608e4	4ed58a82-36fc-460f-aff9-01a5186e0b07
2597a4ca-aa75-434b-a94f-d2061fc608e4	f758bfa5-57fb-4cce-9cde-f5adf07d8a1c
2597a4ca-aa75-434b-a94f-d2061fc608e4	3302da8e-f372-40a7-be29-951344ef33a7
2597a4ca-aa75-434b-a94f-d2061fc608e4	31c9bea2-0956-4699-b3cf-7abb3c967301
3cb1de20-09f4-4261-bd6d-5bbe5f592859	e36e2123-c2c6-4764-9841-b7e8ac0d4aee
4ac8416f-2fcb-427e-98f9-f7e9e310d208	c7f792b7-5efd-45b0-85d6-f2328dc4d0c1
640c6d85-9e75-4bcc-9888-12fce69bb801	4267d9e9-1df6-41a5-b7dc-0e4a7d5a6a38
640c6d85-9e75-4bcc-9888-12fce69bb801	3302da8e-f372-40a7-be29-951344ef33a7
c2c84cc6-e077-4f7f-8328-1d36514b9d29	fd63f58f-5a2b-413e-a1eb-89403ff94508
c2c84cc6-e077-4f7f-8328-1d36514b9d29	1b790592-5715-474f-bf3e-236b957642bf
c2c84cc6-e077-4f7f-8328-1d36514b9d29	4ac8416f-2fcb-427e-98f9-f7e9e310d208
c2c84cc6-e077-4f7f-8328-1d36514b9d29	8d4298f0-399e-4cb1-8a4b-910d17dd958c
d4fbf3f4-3961-4f18-899a-b186f66b7ec0	6e726a93-c9af-4ddb-8a74-fe7f7908f9d9
\.


--
-- Data for Name: credential; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.credential (id, salt, type, user_id, created_date, user_label, secret_data, credential_data, priority) FROM stdin;
88244a39-57d5-4781-805c-665acbb44f24	\N	password	bc1d957b-fa19-4356-b7af-ee08cf6ec8b4	1722862063271	\N	{"value":"PBwLGPrYhrSFnf1/BVsRkVLGV3qTK98HaRUXoOS4EyM=","salt":"S/lg8OC3OHSDJupQM2PHXw==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
72f7c1d3-b941-45d6-b682-8dff5f6141bb	\N	password	e79c7e47-0e29-45cb-b125-3791d34a356f	1722863417344	My password	{"value":"zpAH7aXAzVw39pc7MgFFTfvEsDUfU55S7nWlgqlx5V8=","salt":"pOk9LkyawGCQyjD4evendQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
1a096664-efdf-4129-b1b8-1361e8787684	\N	password	be98932a-2c79-472b-b883-5d5eb4cef5e5	1722866746539	My password	{"value":"vpY+ynnT2OWemFAoXxWItO07syihnvxgRxxlWJa4knU=","salt":"C3cZfSOZvCdCoSyiOq4Z9g==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/jpa-changelog-1.0.0.Final.xml	2024-08-05 12:47:19.574134	1	EXECUTED	9:6f1016664e21e16d26517a4418f5e3df	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.25.1	\N	\N	2862037658
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/db2-jpa-changelog-1.0.0.Final.xml	2024-08-05 12:47:19.636605	2	MARK_RAN	9:828775b1596a07d1200ba1d49e5e3941	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.25.1	\N	\N	2862037658
1.1.0.Beta1	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Beta1.xml	2024-08-05 12:47:20.007713	3	EXECUTED	9:5f090e44a7d595883c1fb61f4b41fd38	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=CLIENT_ATTRIBUTES; createTable tableName=CLIENT_SESSION_NOTE; createTable tableName=APP_NODE_REGISTRATIONS; addColumn table...		\N	4.25.1	\N	\N	2862037658
1.1.0.Final	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Final.xml	2024-08-05 12:47:20.035355	4	EXECUTED	9:c07e577387a3d2c04d1adc9aaad8730e	renameColumn newColumnName=EVENT_TIME, oldColumnName=TIME, tableName=EVENT_ENTITY		\N	4.25.1	\N	\N	2862037658
1.2.0.Beta1	psilva@redhat.com	META-INF/jpa-changelog-1.2.0.Beta1.xml	2024-08-05 12:47:20.478346	5	EXECUTED	9:b68ce996c655922dbcd2fe6b6ae72686	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.25.1	\N	\N	2862037658
1.2.0.Beta1	psilva@redhat.com	META-INF/db2-jpa-changelog-1.2.0.Beta1.xml	2024-08-05 12:47:20.495837	6	MARK_RAN	9:543b5c9989f024fe35c6f6c5a97de88e	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.25.1	\N	\N	2862037658
1.2.0.RC1	bburke@redhat.com	META-INF/jpa-changelog-1.2.0.CR1.xml	2024-08-05 12:47:20.785325	7	EXECUTED	9:765afebbe21cf5bbca048e632df38336	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.25.1	\N	\N	2862037658
1.2.0.RC1	bburke@redhat.com	META-INF/db2-jpa-changelog-1.2.0.CR1.xml	2024-08-05 12:47:20.812299	8	MARK_RAN	9:db4a145ba11a6fdaefb397f6dbf829a1	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.25.1	\N	\N	2862037658
1.2.0.Final	keycloak	META-INF/jpa-changelog-1.2.0.Final.xml	2024-08-05 12:47:20.836164	9	EXECUTED	9:9d05c7be10cdb873f8bcb41bc3a8ab23	update tableName=CLIENT; update tableName=CLIENT; update tableName=CLIENT		\N	4.25.1	\N	\N	2862037658
1.3.0	bburke@redhat.com	META-INF/jpa-changelog-1.3.0.xml	2024-08-05 12:47:21.386658	10	EXECUTED	9:18593702353128d53111f9b1ff0b82b8	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=ADMI...		\N	4.25.1	\N	\N	2862037658
1.4.0	bburke@redhat.com	META-INF/jpa-changelog-1.4.0.xml	2024-08-05 12:47:21.577752	11	EXECUTED	9:6122efe5f090e41a85c0f1c9e52cbb62	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.25.1	\N	\N	2862037658
1.4.0	bburke@redhat.com	META-INF/db2-jpa-changelog-1.4.0.xml	2024-08-05 12:47:21.596956	12	MARK_RAN	9:e1ff28bf7568451453f844c5d54bb0b5	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.25.1	\N	\N	2862037658
1.5.0	bburke@redhat.com	META-INF/jpa-changelog-1.5.0.xml	2024-08-05 12:47:21.671263	13	EXECUTED	9:7af32cd8957fbc069f796b61217483fd	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.25.1	\N	\N	2862037658
1.6.1_from15	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2024-08-05 12:47:21.789754	14	EXECUTED	9:6005e15e84714cd83226bf7879f54190	addColumn tableName=REALM; addColumn tableName=KEYCLOAK_ROLE; addColumn tableName=CLIENT; createTable tableName=OFFLINE_USER_SESSION; createTable tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_US_SES_PK2, tableName=...		\N	4.25.1	\N	\N	2862037658
1.6.1_from16-pre	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2024-08-05 12:47:21.795302	15	MARK_RAN	9:bf656f5a2b055d07f314431cae76f06c	delete tableName=OFFLINE_CLIENT_SESSION; delete tableName=OFFLINE_USER_SESSION		\N	4.25.1	\N	\N	2862037658
1.6.1_from16	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2024-08-05 12:47:21.812745	16	MARK_RAN	9:f8dadc9284440469dcf71e25ca6ab99b	dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_US_SES_PK, tableName=OFFLINE_USER_SESSION; dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_CL_SES_PK, tableName=OFFLINE_CLIENT_SESSION; addColumn tableName=OFFLINE_USER_SESSION; update tableName=OF...		\N	4.25.1	\N	\N	2862037658
1.6.1	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2024-08-05 12:47:21.919033	17	EXECUTED	9:d41d8cd98f00b204e9800998ecf8427e	empty		\N	4.25.1	\N	\N	2862037658
1.7.0	bburke@redhat.com	META-INF/jpa-changelog-1.7.0.xml	2024-08-05 12:47:22.309342	18	EXECUTED	9:3368ff0be4c2855ee2dd9ca813b38d8e	createTable tableName=KEYCLOAK_GROUP; createTable tableName=GROUP_ROLE_MAPPING; createTable tableName=GROUP_ATTRIBUTE; createTable tableName=USER_GROUP_MEMBERSHIP; createTable tableName=REALM_DEFAULT_GROUPS; addColumn tableName=IDENTITY_PROVIDER; ...		\N	4.25.1	\N	\N	2862037658
1.8.0	mposolda@redhat.com	META-INF/jpa-changelog-1.8.0.xml	2024-08-05 12:47:22.558871	19	EXECUTED	9:8ac2fb5dd030b24c0570a763ed75ed20	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.25.1	\N	\N	2862037658
1.8.0-2	keycloak	META-INF/jpa-changelog-1.8.0.xml	2024-08-05 12:47:22.580013	20	EXECUTED	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.25.1	\N	\N	2862037658
1.8.0	mposolda@redhat.com	META-INF/db2-jpa-changelog-1.8.0.xml	2024-08-05 12:47:22.597323	21	MARK_RAN	9:831e82914316dc8a57dc09d755f23c51	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.25.1	\N	\N	2862037658
1.8.0-2	keycloak	META-INF/db2-jpa-changelog-1.8.0.xml	2024-08-05 12:47:22.618268	22	MARK_RAN	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.25.1	\N	\N	2862037658
1.9.0	mposolda@redhat.com	META-INF/jpa-changelog-1.9.0.xml	2024-08-05 12:47:22.731387	23	EXECUTED	9:bc3d0f9e823a69dc21e23e94c7a94bb1	update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=REALM; update tableName=REALM; customChange; dr...		\N	4.25.1	\N	\N	2862037658
1.9.1	keycloak	META-INF/jpa-changelog-1.9.1.xml	2024-08-05 12:47:22.747334	24	EXECUTED	9:c9999da42f543575ab790e76439a2679	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=PUBLIC_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.25.1	\N	\N	2862037658
1.9.1	keycloak	META-INF/db2-jpa-changelog-1.9.1.xml	2024-08-05 12:47:22.752671	25	MARK_RAN	9:0d6c65c6f58732d81569e77b10ba301d	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.25.1	\N	\N	2862037658
1.9.2	keycloak	META-INF/jpa-changelog-1.9.2.xml	2024-08-05 12:47:23.041126	26	EXECUTED	9:fc576660fc016ae53d2d4778d84d86d0	createIndex indexName=IDX_USER_EMAIL, tableName=USER_ENTITY; createIndex indexName=IDX_USER_ROLE_MAPPING, tableName=USER_ROLE_MAPPING; createIndex indexName=IDX_USER_GROUP_MAPPING, tableName=USER_GROUP_MEMBERSHIP; createIndex indexName=IDX_USER_CO...		\N	4.25.1	\N	\N	2862037658
authz-2.0.0	psilva@redhat.com	META-INF/jpa-changelog-authz-2.0.0.xml	2024-08-05 12:47:23.344845	27	EXECUTED	9:43ed6b0da89ff77206289e87eaa9c024	createTable tableName=RESOURCE_SERVER; addPrimaryKey constraintName=CONSTRAINT_FARS, tableName=RESOURCE_SERVER; addUniqueConstraint constraintName=UK_AU8TT6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER; createTable tableName=RESOURCE_SERVER_RESOU...		\N	4.25.1	\N	\N	2862037658
authz-2.5.1	psilva@redhat.com	META-INF/jpa-changelog-authz-2.5.1.xml	2024-08-05 12:47:23.357888	28	EXECUTED	9:44bae577f551b3738740281eceb4ea70	update tableName=RESOURCE_SERVER_POLICY		\N	4.25.1	\N	\N	2862037658
2.1.0-KEYCLOAK-5461	bburke@redhat.com	META-INF/jpa-changelog-2.1.0.xml	2024-08-05 12:47:23.536455	29	EXECUTED	9:bd88e1f833df0420b01e114533aee5e8	createTable tableName=BROKER_LINK; createTable tableName=FED_USER_ATTRIBUTE; createTable tableName=FED_USER_CONSENT; createTable tableName=FED_USER_CONSENT_ROLE; createTable tableName=FED_USER_CONSENT_PROT_MAPPER; createTable tableName=FED_USER_CR...		\N	4.25.1	\N	\N	2862037658
2.2.0	bburke@redhat.com	META-INF/jpa-changelog-2.2.0.xml	2024-08-05 12:47:23.576744	30	EXECUTED	9:a7022af5267f019d020edfe316ef4371	addColumn tableName=ADMIN_EVENT_ENTITY; createTable tableName=CREDENTIAL_ATTRIBUTE; createTable tableName=FED_CREDENTIAL_ATTRIBUTE; modifyDataType columnName=VALUE, tableName=CREDENTIAL; addForeignKeyConstraint baseTableName=FED_CREDENTIAL_ATTRIBU...		\N	4.25.1	\N	\N	2862037658
2.3.0	bburke@redhat.com	META-INF/jpa-changelog-2.3.0.xml	2024-08-05 12:47:23.637023	31	EXECUTED	9:fc155c394040654d6a79227e56f5e25a	createTable tableName=FEDERATED_USER; addPrimaryKey constraintName=CONSTR_FEDERATED_USER, tableName=FEDERATED_USER; dropDefaultValue columnName=TOTP, tableName=USER_ENTITY; dropColumn columnName=TOTP, tableName=USER_ENTITY; addColumn tableName=IDE...		\N	4.25.1	\N	\N	2862037658
2.4.0	bburke@redhat.com	META-INF/jpa-changelog-2.4.0.xml	2024-08-05 12:47:23.657928	32	EXECUTED	9:eac4ffb2a14795e5dc7b426063e54d88	customChange		\N	4.25.1	\N	\N	2862037658
2.5.0	bburke@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2024-08-05 12:47:23.710678	33	EXECUTED	9:54937c05672568c4c64fc9524c1e9462	customChange; modifyDataType columnName=USER_ID, tableName=OFFLINE_USER_SESSION		\N	4.25.1	\N	\N	2862037658
2.5.0-unicode-oracle	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2024-08-05 12:47:23.718018	34	MARK_RAN	9:3a32bace77c84d7678d035a7f5a8084e	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.25.1	\N	\N	2862037658
2.5.0-unicode-other-dbs	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2024-08-05 12:47:23.833729	35	EXECUTED	9:33d72168746f81f98ae3a1e8e0ca3554	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.25.1	\N	\N	2862037658
2.5.0-duplicate-email-support	slawomir@dabek.name	META-INF/jpa-changelog-2.5.0.xml	2024-08-05 12:47:23.849259	36	EXECUTED	9:61b6d3d7a4c0e0024b0c839da283da0c	addColumn tableName=REALM		\N	4.25.1	\N	\N	2862037658
2.5.0-unique-group-names	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2024-08-05 12:47:23.877926	37	EXECUTED	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.25.1	\N	\N	2862037658
2.5.1	bburke@redhat.com	META-INF/jpa-changelog-2.5.1.xml	2024-08-05 12:47:23.889731	38	EXECUTED	9:a2b870802540cb3faa72098db5388af3	addColumn tableName=FED_USER_CONSENT		\N	4.25.1	\N	\N	2862037658
3.0.0	bburke@redhat.com	META-INF/jpa-changelog-3.0.0.xml	2024-08-05 12:47:23.899832	39	EXECUTED	9:132a67499ba24bcc54fb5cbdcfe7e4c0	addColumn tableName=IDENTITY_PROVIDER		\N	4.25.1	\N	\N	2862037658
3.2.0-fix	keycloak	META-INF/jpa-changelog-3.2.0.xml	2024-08-05 12:47:23.9039	40	MARK_RAN	9:938f894c032f5430f2b0fafb1a243462	addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS		\N	4.25.1	\N	\N	2862037658
3.2.0-fix-with-keycloak-5416	keycloak	META-INF/jpa-changelog-3.2.0.xml	2024-08-05 12:47:23.911696	41	MARK_RAN	9:845c332ff1874dc5d35974b0babf3006	dropIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS; addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS; createIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS		\N	4.25.1	\N	\N	2862037658
3.2.0-fix-offline-sessions	hmlnarik	META-INF/jpa-changelog-3.2.0.xml	2024-08-05 12:47:23.935746	42	EXECUTED	9:fc86359c079781adc577c5a217e4d04c	customChange		\N	4.25.1	\N	\N	2862037658
3.2.0-fixed	keycloak	META-INF/jpa-changelog-3.2.0.xml	2024-08-05 12:47:24.407383	43	EXECUTED	9:59a64800e3c0d09b825f8a3b444fa8f4	addColumn tableName=REALM; dropPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_PK2, tableName=OFFLINE_CLIENT_SESSION; dropColumn columnName=CLIENT_SESSION_ID, tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_P...		\N	4.25.1	\N	\N	2862037658
3.3.0	keycloak	META-INF/jpa-changelog-3.3.0.xml	2024-08-05 12:47:24.41913	44	EXECUTED	9:d48d6da5c6ccf667807f633fe489ce88	addColumn tableName=USER_ENTITY		\N	4.25.1	\N	\N	2862037658
authz-3.4.0.CR1-resource-server-pk-change-part1	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2024-08-05 12:47:24.432472	45	EXECUTED	9:dde36f7973e80d71fceee683bc5d2951	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_RESOURCE; addColumn tableName=RESOURCE_SERVER_SCOPE		\N	4.25.1	\N	\N	2862037658
authz-3.4.0.CR1-resource-server-pk-change-part2-KEYCLOAK-6095	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2024-08-05 12:47:24.451427	46	EXECUTED	9:b855e9b0a406b34fa323235a0cf4f640	customChange		\N	4.25.1	\N	\N	2862037658
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2024-08-05 12:47:24.456137	47	MARK_RAN	9:51abbacd7b416c50c4421a8cabf7927e	dropIndex indexName=IDX_RES_SERV_POL_RES_SERV, tableName=RESOURCE_SERVER_POLICY; dropIndex indexName=IDX_RES_SRV_RES_RES_SRV, tableName=RESOURCE_SERVER_RESOURCE; dropIndex indexName=IDX_RES_SRV_SCOPE_RES_SRV, tableName=RESOURCE_SERVER_SCOPE		\N	4.25.1	\N	\N	2862037658
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed-nodropindex	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2024-08-05 12:47:24.541093	48	EXECUTED	9:bdc99e567b3398bac83263d375aad143	addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_POLICY; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_RESOURCE; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, ...		\N	4.25.1	\N	\N	2862037658
authn-3.4.0.CR1-refresh-token-max-reuse	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2024-08-05 12:47:24.551475	49	EXECUTED	9:d198654156881c46bfba39abd7769e69	addColumn tableName=REALM		\N	4.25.1	\N	\N	2862037658
3.4.0	keycloak	META-INF/jpa-changelog-3.4.0.xml	2024-08-05 12:47:24.677332	50	EXECUTED	9:cfdd8736332ccdd72c5256ccb42335db	addPrimaryKey constraintName=CONSTRAINT_REALM_DEFAULT_ROLES, tableName=REALM_DEFAULT_ROLES; addPrimaryKey constraintName=CONSTRAINT_COMPOSITE_ROLE, tableName=COMPOSITE_ROLE; addPrimaryKey constraintName=CONSTR_REALM_DEFAULT_GROUPS, tableName=REALM...		\N	4.25.1	\N	\N	2862037658
3.4.0-KEYCLOAK-5230	hmlnarik@redhat.com	META-INF/jpa-changelog-3.4.0.xml	2024-08-05 12:47:24.863062	51	EXECUTED	9:7c84de3d9bd84d7f077607c1a4dcb714	createIndex indexName=IDX_FU_ATTRIBUTE, tableName=FED_USER_ATTRIBUTE; createIndex indexName=IDX_FU_CONSENT, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CONSENT_RU, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CREDENTIAL, t...		\N	4.25.1	\N	\N	2862037658
3.4.1	psilva@redhat.com	META-INF/jpa-changelog-3.4.1.xml	2024-08-05 12:47:24.876386	52	EXECUTED	9:5a6bb36cbefb6a9d6928452c0852af2d	modifyDataType columnName=VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.25.1	\N	\N	2862037658
3.4.2	keycloak	META-INF/jpa-changelog-3.4.2.xml	2024-08-05 12:47:24.903831	53	EXECUTED	9:8f23e334dbc59f82e0a328373ca6ced0	update tableName=REALM		\N	4.25.1	\N	\N	2862037658
3.4.2-KEYCLOAK-5172	mkanis@redhat.com	META-INF/jpa-changelog-3.4.2.xml	2024-08-05 12:47:24.911914	54	EXECUTED	9:9156214268f09d970cdf0e1564d866af	update tableName=CLIENT		\N	4.25.1	\N	\N	2862037658
4.0.0-KEYCLOAK-6335	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2024-08-05 12:47:24.93939	55	EXECUTED	9:db806613b1ed154826c02610b7dbdf74	createTable tableName=CLIENT_AUTH_FLOW_BINDINGS; addPrimaryKey constraintName=C_CLI_FLOW_BIND, tableName=CLIENT_AUTH_FLOW_BINDINGS		\N	4.25.1	\N	\N	2862037658
4.0.0-CLEANUP-UNUSED-TABLE	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2024-08-05 12:47:24.950275	56	EXECUTED	9:229a041fb72d5beac76bb94a5fa709de	dropTable tableName=CLIENT_IDENTITY_PROV_MAPPING		\N	4.25.1	\N	\N	2862037658
4.0.0-KEYCLOAK-6228	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2024-08-05 12:47:25.041295	57	EXECUTED	9:079899dade9c1e683f26b2aa9ca6ff04	dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; dropNotNullConstraint columnName=CLIENT_ID, tableName=USER_CONSENT; addColumn tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHO...		\N	4.25.1	\N	\N	2862037658
4.0.0-KEYCLOAK-5579-fixed	mposolda@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2024-08-05 12:47:25.296871	58	EXECUTED	9:139b79bcbbfe903bb1c2d2a4dbf001d9	dropForeignKeyConstraint baseTableName=CLIENT_TEMPLATE_ATTRIBUTES, constraintName=FK_CL_TEMPL_ATTR_TEMPL; renameTable newTableName=CLIENT_SCOPE_ATTRIBUTES, oldTableName=CLIENT_TEMPLATE_ATTRIBUTES; renameColumn newColumnName=SCOPE_ID, oldColumnName...		\N	4.25.1	\N	\N	2862037658
authz-4.0.0.CR1	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.CR1.xml	2024-08-05 12:47:25.37427	59	EXECUTED	9:b55738ad889860c625ba2bf483495a04	createTable tableName=RESOURCE_SERVER_PERM_TICKET; addPrimaryKey constraintName=CONSTRAINT_FAPMT, tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRHO213XCX4WNKOG82SSPMT...		\N	4.25.1	\N	\N	2862037658
authz-4.0.0.Beta3	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.Beta3.xml	2024-08-05 12:47:25.401985	60	EXECUTED	9:e0057eac39aa8fc8e09ac6cfa4ae15fe	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRPO2128CX4WNKOG82SSRFY, referencedTableName=RESOURCE_SERVER_POLICY		\N	4.25.1	\N	\N	2862037658
authz-4.2.0.Final	mhajas@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2024-08-05 12:47:25.42849	61	EXECUTED	9:42a33806f3a0443fe0e7feeec821326c	createTable tableName=RESOURCE_URIS; addForeignKeyConstraint baseTableName=RESOURCE_URIS, constraintName=FK_RESOURCE_SERVER_URIS, referencedTableName=RESOURCE_SERVER_RESOURCE; customChange; dropColumn columnName=URI, tableName=RESOURCE_SERVER_RESO...		\N	4.25.1	\N	\N	2862037658
authz-4.2.0.Final-KEYCLOAK-9944	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2024-08-05 12:47:25.445258	62	EXECUTED	9:9968206fca46eecc1f51db9c024bfe56	addPrimaryKey constraintName=CONSTRAINT_RESOUR_URIS_PK, tableName=RESOURCE_URIS		\N	4.25.1	\N	\N	2862037658
4.2.0-KEYCLOAK-6313	wadahiro@gmail.com	META-INF/jpa-changelog-4.2.0.xml	2024-08-05 12:47:25.45904	63	EXECUTED	9:92143a6daea0a3f3b8f598c97ce55c3d	addColumn tableName=REQUIRED_ACTION_PROVIDER		\N	4.25.1	\N	\N	2862037658
4.3.0-KEYCLOAK-7984	wadahiro@gmail.com	META-INF/jpa-changelog-4.3.0.xml	2024-08-05 12:47:25.472416	64	EXECUTED	9:82bab26a27195d889fb0429003b18f40	update tableName=REQUIRED_ACTION_PROVIDER		\N	4.25.1	\N	\N	2862037658
4.6.0-KEYCLOAK-7950	psilva@redhat.com	META-INF/jpa-changelog-4.6.0.xml	2024-08-05 12:47:25.480561	65	EXECUTED	9:e590c88ddc0b38b0ae4249bbfcb5abc3	update tableName=RESOURCE_SERVER_RESOURCE		\N	4.25.1	\N	\N	2862037658
4.6.0-KEYCLOAK-8377	keycloak	META-INF/jpa-changelog-4.6.0.xml	2024-08-05 12:47:25.540076	66	EXECUTED	9:5c1f475536118dbdc38d5d7977950cc0	createTable tableName=ROLE_ATTRIBUTE; addPrimaryKey constraintName=CONSTRAINT_ROLE_ATTRIBUTE_PK, tableName=ROLE_ATTRIBUTE; addForeignKeyConstraint baseTableName=ROLE_ATTRIBUTE, constraintName=FK_ROLE_ATTRIBUTE_ID, referencedTableName=KEYCLOAK_ROLE...		\N	4.25.1	\N	\N	2862037658
4.6.0-KEYCLOAK-8555	gideonray@gmail.com	META-INF/jpa-changelog-4.6.0.xml	2024-08-05 12:47:25.555641	67	EXECUTED	9:e7c9f5f9c4d67ccbbcc215440c718a17	createIndex indexName=IDX_COMPONENT_PROVIDER_TYPE, tableName=COMPONENT		\N	4.25.1	\N	\N	2862037658
4.7.0-KEYCLOAK-1267	sguilhen@redhat.com	META-INF/jpa-changelog-4.7.0.xml	2024-08-05 12:47:25.567122	68	EXECUTED	9:88e0bfdda924690d6f4e430c53447dd5	addColumn tableName=REALM		\N	4.25.1	\N	\N	2862037658
4.7.0-KEYCLOAK-7275	keycloak	META-INF/jpa-changelog-4.7.0.xml	2024-08-05 12:47:25.610245	69	EXECUTED	9:f53177f137e1c46b6a88c59ec1cb5218	renameColumn newColumnName=CREATED_ON, oldColumnName=LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION; addNotNullConstraint columnName=CREATED_ON, tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_USER_SESSION; customChange; createIn...		\N	4.25.1	\N	\N	2862037658
4.8.0-KEYCLOAK-8835	sguilhen@redhat.com	META-INF/jpa-changelog-4.8.0.xml	2024-08-05 12:47:25.651104	70	EXECUTED	9:a74d33da4dc42a37ec27121580d1459f	addNotNullConstraint columnName=SSO_MAX_LIFESPAN_REMEMBER_ME, tableName=REALM; addNotNullConstraint columnName=SSO_IDLE_TIMEOUT_REMEMBER_ME, tableName=REALM		\N	4.25.1	\N	\N	2862037658
authz-7.0.0-KEYCLOAK-10443	psilva@redhat.com	META-INF/jpa-changelog-authz-7.0.0.xml	2024-08-05 12:47:25.668316	71	EXECUTED	9:fd4ade7b90c3b67fae0bfcfcb42dfb5f	addColumn tableName=RESOURCE_SERVER		\N	4.25.1	\N	\N	2862037658
8.0.0-adding-credential-columns	keycloak	META-INF/jpa-changelog-8.0.0.xml	2024-08-05 12:47:25.692498	72	EXECUTED	9:aa072ad090bbba210d8f18781b8cebf4	addColumn tableName=CREDENTIAL; addColumn tableName=FED_USER_CREDENTIAL		\N	4.25.1	\N	\N	2862037658
8.0.0-updating-credential-data-not-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2024-08-05 12:47:25.713763	73	EXECUTED	9:1ae6be29bab7c2aa376f6983b932be37	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.25.1	\N	\N	2862037658
8.0.0-updating-credential-data-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2024-08-05 12:47:25.719715	74	MARK_RAN	9:14706f286953fc9a25286dbd8fb30d97	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.25.1	\N	\N	2862037658
8.0.0-credential-cleanup-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2024-08-05 12:47:25.761094	75	EXECUTED	9:2b9cc12779be32c5b40e2e67711a218b	dropDefaultValue columnName=COUNTER, tableName=CREDENTIAL; dropDefaultValue columnName=DIGITS, tableName=CREDENTIAL; dropDefaultValue columnName=PERIOD, tableName=CREDENTIAL; dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; dropColumn ...		\N	4.25.1	\N	\N	2862037658
8.0.0-resource-tag-support	keycloak	META-INF/jpa-changelog-8.0.0.xml	2024-08-05 12:47:25.777554	76	EXECUTED	9:91fa186ce7a5af127a2d7a91ee083cc5	addColumn tableName=MIGRATION_MODEL; createIndex indexName=IDX_UPDATE_TIME, tableName=MIGRATION_MODEL		\N	4.25.1	\N	\N	2862037658
9.0.0-always-display-client	keycloak	META-INF/jpa-changelog-9.0.0.xml	2024-08-05 12:47:25.789276	77	EXECUTED	9:6335e5c94e83a2639ccd68dd24e2e5ad	addColumn tableName=CLIENT		\N	4.25.1	\N	\N	2862037658
9.0.0-drop-constraints-for-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2024-08-05 12:47:25.79562	78	MARK_RAN	9:6bdb5658951e028bfe16fa0a8228b530	dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5PMT, tableName=RESOURCE_SERVER_PERM_TICKET; dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER_RESOURCE; dropPrimaryKey constraintName=CONSTRAINT_O...		\N	4.25.1	\N	\N	2862037658
9.0.0-increase-column-size-federated-fk	keycloak	META-INF/jpa-changelog-9.0.0.xml	2024-08-05 12:47:25.862275	79	EXECUTED	9:d5bc15a64117ccad481ce8792d4c608f	modifyDataType columnName=CLIENT_ID, tableName=FED_USER_CONSENT; modifyDataType columnName=CLIENT_REALM_CONSTRAINT, tableName=KEYCLOAK_ROLE; modifyDataType columnName=OWNER, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=CLIENT_ID, ta...		\N	4.25.1	\N	\N	2862037658
9.0.0-recreate-constraints-after-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2024-08-05 12:47:25.874524	80	MARK_RAN	9:077cba51999515f4d3e7ad5619ab592c	addNotNullConstraint columnName=CLIENT_ID, tableName=OFFLINE_CLIENT_SESSION; addNotNullConstraint columnName=OWNER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNullConstraint columnName=REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNull...		\N	4.25.1	\N	\N	2862037658
9.0.1-add-index-to-client.client_id	keycloak	META-INF/jpa-changelog-9.0.1.xml	2024-08-05 12:47:25.901196	81	EXECUTED	9:be969f08a163bf47c6b9e9ead8ac2afb	createIndex indexName=IDX_CLIENT_ID, tableName=CLIENT		\N	4.25.1	\N	\N	2862037658
9.0.1-KEYCLOAK-12579-drop-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2024-08-05 12:47:25.905004	82	MARK_RAN	9:6d3bb4408ba5a72f39bd8a0b301ec6e3	dropUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.25.1	\N	\N	2862037658
9.0.1-KEYCLOAK-12579-add-not-null-constraint	keycloak	META-INF/jpa-changelog-9.0.1.xml	2024-08-05 12:47:25.922311	83	EXECUTED	9:966bda61e46bebf3cc39518fbed52fa7	addNotNullConstraint columnName=PARENT_GROUP, tableName=KEYCLOAK_GROUP		\N	4.25.1	\N	\N	2862037658
9.0.1-KEYCLOAK-12579-recreate-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2024-08-05 12:47:25.926025	84	MARK_RAN	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.25.1	\N	\N	2862037658
9.0.1-add-index-to-events	keycloak	META-INF/jpa-changelog-9.0.1.xml	2024-08-05 12:47:25.94463	85	EXECUTED	9:7d93d602352a30c0c317e6a609b56599	createIndex indexName=IDX_EVENT_TIME, tableName=EVENT_ENTITY		\N	4.25.1	\N	\N	2862037658
map-remove-ri	keycloak	META-INF/jpa-changelog-11.0.0.xml	2024-08-05 12:47:25.958904	86	EXECUTED	9:71c5969e6cdd8d7b6f47cebc86d37627	dropForeignKeyConstraint baseTableName=REALM, constraintName=FK_TRAF444KK6QRKMS7N56AIWQ5Y; dropForeignKeyConstraint baseTableName=KEYCLOAK_ROLE, constraintName=FK_KJHO5LE2C0RAL09FL8CM9WFW9		\N	4.25.1	\N	\N	2862037658
map-remove-ri	keycloak	META-INF/jpa-changelog-12.0.0.xml	2024-08-05 12:47:25.975396	87	EXECUTED	9:a9ba7d47f065f041b7da856a81762021	dropForeignKeyConstraint baseTableName=REALM_DEFAULT_GROUPS, constraintName=FK_DEF_GROUPS_GROUP; dropForeignKeyConstraint baseTableName=REALM_DEFAULT_ROLES, constraintName=FK_H4WPD7W4HSOOLNI3H0SW7BTJE; dropForeignKeyConstraint baseTableName=CLIENT...		\N	4.25.1	\N	\N	2862037658
12.1.0-add-realm-localization-table	keycloak	META-INF/jpa-changelog-12.0.0.xml	2024-08-05 12:47:26.027204	88	EXECUTED	9:fffabce2bc01e1a8f5110d5278500065	createTable tableName=REALM_LOCALIZATIONS; addPrimaryKey tableName=REALM_LOCALIZATIONS		\N	4.25.1	\N	\N	2862037658
default-roles	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-08-05 12:47:26.075469	89	EXECUTED	9:fa8a5b5445e3857f4b010bafb5009957	addColumn tableName=REALM; customChange		\N	4.25.1	\N	\N	2862037658
default-roles-cleanup	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-08-05 12:47:26.091875	90	EXECUTED	9:67ac3241df9a8582d591c5ed87125f39	dropTable tableName=REALM_DEFAULT_ROLES; dropTable tableName=CLIENT_DEFAULT_ROLES		\N	4.25.1	\N	\N	2862037658
13.0.0-KEYCLOAK-16844	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-08-05 12:47:26.109925	91	EXECUTED	9:ad1194d66c937e3ffc82386c050ba089	createIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.25.1	\N	\N	2862037658
map-remove-ri-13.0.0	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-08-05 12:47:26.122632	92	EXECUTED	9:d9be619d94af5a2f5d07b9f003543b91	dropForeignKeyConstraint baseTableName=DEFAULT_CLIENT_SCOPE, constraintName=FK_R_DEF_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SCOPE_CLIENT, constraintName=FK_C_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SC...		\N	4.25.1	\N	\N	2862037658
13.0.0-KEYCLOAK-17992-drop-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-08-05 12:47:26.12546	93	MARK_RAN	9:544d201116a0fcc5a5da0925fbbc3bde	dropPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CLSCOPE_CL, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CL_CLSCOPE, tableName=CLIENT_SCOPE_CLIENT		\N	4.25.1	\N	\N	2862037658
13.0.0-increase-column-size-federated	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-08-05 12:47:26.140278	94	EXECUTED	9:43c0c1055b6761b4b3e89de76d612ccf	modifyDataType columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; modifyDataType columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT		\N	4.25.1	\N	\N	2862037658
13.0.0-KEYCLOAK-17992-recreate-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-08-05 12:47:26.143772	95	MARK_RAN	9:8bd711fd0330f4fe980494ca43ab1139	addNotNullConstraint columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; addNotNullConstraint columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT; addPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; createIndex indexName=...		\N	4.25.1	\N	\N	2862037658
json-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-13.0.0.xml	2024-08-05 12:47:26.159845	96	EXECUTED	9:e07d2bc0970c348bb06fb63b1f82ddbf	addColumn tableName=REALM_ATTRIBUTE; update tableName=REALM_ATTRIBUTE; dropColumn columnName=VALUE, tableName=REALM_ATTRIBUTE; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=REALM_ATTRIBUTE		\N	4.25.1	\N	\N	2862037658
14.0.0-KEYCLOAK-11019	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-08-05 12:47:26.219406	97	EXECUTED	9:24fb8611e97f29989bea412aa38d12b7	createIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USER, tableName=OFFLINE_USER_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.25.1	\N	\N	2862037658
14.0.0-KEYCLOAK-18286	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-08-05 12:47:26.225098	98	MARK_RAN	9:259f89014ce2506ee84740cbf7163aa7	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.25.1	\N	\N	2862037658
14.0.0-KEYCLOAK-18286-revert	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-08-05 12:47:26.24797	99	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.25.1	\N	\N	2862037658
14.0.0-KEYCLOAK-18286-supported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-08-05 12:47:26.272261	100	EXECUTED	9:60ca84a0f8c94ec8c3504a5a3bc88ee8	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.25.1	\N	\N	2862037658
14.0.0-KEYCLOAK-18286-unsupported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-08-05 12:47:26.275713	101	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.25.1	\N	\N	2862037658
KEYCLOAK-17267-add-index-to-user-attributes	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-08-05 12:47:26.294579	102	EXECUTED	9:0b305d8d1277f3a89a0a53a659ad274c	createIndex indexName=IDX_USER_ATTRIBUTE_NAME, tableName=USER_ATTRIBUTE		\N	4.25.1	\N	\N	2862037658
KEYCLOAK-18146-add-saml-art-binding-identifier	keycloak	META-INF/jpa-changelog-14.0.0.xml	2024-08-05 12:47:26.314078	103	EXECUTED	9:2c374ad2cdfe20e2905a84c8fac48460	customChange		\N	4.25.1	\N	\N	2862037658
15.0.0-KEYCLOAK-18467	keycloak	META-INF/jpa-changelog-15.0.0.xml	2024-08-05 12:47:26.333718	104	EXECUTED	9:47a760639ac597360a8219f5b768b4de	addColumn tableName=REALM_LOCALIZATIONS; update tableName=REALM_LOCALIZATIONS; dropColumn columnName=TEXTS, tableName=REALM_LOCALIZATIONS; renameColumn newColumnName=TEXTS, oldColumnName=TEXTS_NEW, tableName=REALM_LOCALIZATIONS; addNotNullConstrai...		\N	4.25.1	\N	\N	2862037658
17.0.0-9562	keycloak	META-INF/jpa-changelog-17.0.0.xml	2024-08-05 12:47:26.349321	105	EXECUTED	9:a6272f0576727dd8cad2522335f5d99e	createIndex indexName=IDX_USER_SERVICE_ACCOUNT, tableName=USER_ENTITY		\N	4.25.1	\N	\N	2862037658
18.0.0-10625-IDX_ADMIN_EVENT_TIME	keycloak	META-INF/jpa-changelog-18.0.0.xml	2024-08-05 12:47:26.38967	106	EXECUTED	9:015479dbd691d9cc8669282f4828c41d	createIndex indexName=IDX_ADMIN_EVENT_TIME, tableName=ADMIN_EVENT_ENTITY		\N	4.25.1	\N	\N	2862037658
18.0.15-30992-index-consent	keycloak	META-INF/jpa-changelog-18.0.15.xml	2024-08-05 12:47:26.423153	107	EXECUTED	9:80071ede7a05604b1f4906f3bf3b00f0	createIndex indexName=IDX_USCONSENT_SCOPE_ID, tableName=USER_CONSENT_CLIENT_SCOPE		\N	4.25.1	\N	\N	2862037658
19.0.0-10135	keycloak	META-INF/jpa-changelog-19.0.0.xml	2024-08-05 12:47:26.453843	108	EXECUTED	9:9518e495fdd22f78ad6425cc30630221	customChange		\N	4.25.1	\N	\N	2862037658
20.0.0-12964-supported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2024-08-05 12:47:26.475307	109	EXECUTED	9:e5f243877199fd96bcc842f27a1656ac	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.25.1	\N	\N	2862037658
20.0.0-12964-unsupported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2024-08-05 12:47:26.479458	110	MARK_RAN	9:1a6fcaa85e20bdeae0a9ce49b41946a5	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.25.1	\N	\N	2862037658
client-attributes-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-20.0.0.xml	2024-08-05 12:47:26.516119	111	EXECUTED	9:3f332e13e90739ed0c35b0b25b7822ca	addColumn tableName=CLIENT_ATTRIBUTES; update tableName=CLIENT_ATTRIBUTES; dropColumn columnName=VALUE, tableName=CLIENT_ATTRIBUTES; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=CLIENT_ATTRIBUTES		\N	4.25.1	\N	\N	2862037658
21.0.2-17277	keycloak	META-INF/jpa-changelog-21.0.2.xml	2024-08-05 12:47:26.535273	112	EXECUTED	9:7ee1f7a3fb8f5588f171fb9a6ab623c0	customChange		\N	4.25.1	\N	\N	2862037658
21.1.0-19404	keycloak	META-INF/jpa-changelog-21.1.0.xml	2024-08-05 12:47:26.623086	113	EXECUTED	9:3d7e830b52f33676b9d64f7f2b2ea634	modifyDataType columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=LOGIC, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=POLICY_ENFORCE_MODE, tableName=RESOURCE_SERVER		\N	4.25.1	\N	\N	2862037658
21.1.0-19404-2	keycloak	META-INF/jpa-changelog-21.1.0.xml	2024-08-05 12:47:26.630534	114	MARK_RAN	9:627d032e3ef2c06c0e1f73d2ae25c26c	addColumn tableName=RESOURCE_SERVER_POLICY; update tableName=RESOURCE_SERVER_POLICY; dropColumn columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; renameColumn newColumnName=DECISION_STRATEGY, oldColumnName=DECISION_STRATEGY_NEW, tabl...		\N	4.25.1	\N	\N	2862037658
22.0.0-17484-updated	keycloak	META-INF/jpa-changelog-22.0.0.xml	2024-08-05 12:47:26.645739	115	EXECUTED	9:90af0bfd30cafc17b9f4d6eccd92b8b3	customChange		\N	4.25.1	\N	\N	2862037658
22.0.5-24031	keycloak	META-INF/jpa-changelog-22.0.0.xml	2024-08-05 12:47:26.653665	116	MARK_RAN	9:a60d2d7b315ec2d3eba9e2f145f9df28	customChange		\N	4.25.1	\N	\N	2862037658
23.0.0-12062	keycloak	META-INF/jpa-changelog-23.0.0.xml	2024-08-05 12:47:26.665127	117	EXECUTED	9:2168fbe728fec46ae9baf15bf80927b8	addColumn tableName=COMPONENT_CONFIG; update tableName=COMPONENT_CONFIG; dropColumn columnName=VALUE, tableName=COMPONENT_CONFIG; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=COMPONENT_CONFIG		\N	4.25.1	\N	\N	2862037658
23.0.0-17258	keycloak	META-INF/jpa-changelog-23.0.0.xml	2024-08-05 12:47:26.673258	118	EXECUTED	9:36506d679a83bbfda85a27ea1864dca8	addColumn tableName=EVENT_ENTITY		\N	4.25.1	\N	\N	2862037658
24.0.0-9758	keycloak	META-INF/jpa-changelog-24.0.0.xml	2024-08-05 12:47:26.720589	119	EXECUTED	9:502c557a5189f600f0f445a9b49ebbce	addColumn tableName=USER_ATTRIBUTE; addColumn tableName=FED_USER_ATTRIBUTE; createIndex indexName=USER_ATTR_LONG_VALUES, tableName=USER_ATTRIBUTE; createIndex indexName=FED_USER_ATTR_LONG_VALUES, tableName=FED_USER_ATTRIBUTE; createIndex indexName...		\N	4.25.1	\N	\N	2862037658
24.0.0-9758-2	keycloak	META-INF/jpa-changelog-24.0.0.xml	2024-08-05 12:47:26.732516	120	EXECUTED	9:bf0fdee10afdf597a987adbf291db7b2	customChange		\N	4.25.1	\N	\N	2862037658
24.0.0-26618-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.0.xml	2024-08-05 12:47:26.748296	121	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.25.1	\N	\N	2862037658
24.0.0-26618-reindex	keycloak	META-INF/jpa-changelog-24.0.0.xml	2024-08-05 12:47:26.768241	122	EXECUTED	9:08707c0f0db1cef6b352db03a60edc7f	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.25.1	\N	\N	2862037658
24.0.2-27228	keycloak	META-INF/jpa-changelog-24.0.2.xml	2024-08-05 12:47:26.779179	123	EXECUTED	9:eaee11f6b8aa25d2cc6a84fb86fc6238	customChange		\N	4.25.1	\N	\N	2862037658
24.0.2-27967-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.2.xml	2024-08-05 12:47:26.785681	124	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.25.1	\N	\N	2862037658
24.0.2-27967-reindex	keycloak	META-INF/jpa-changelog-24.0.2.xml	2024-08-05 12:47:26.790769	125	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.25.1	\N	\N	2862037658
25.0.0-28265-tables	keycloak	META-INF/jpa-changelog-25.0.0.xml	2024-08-05 12:47:26.803394	126	EXECUTED	9:deda2df035df23388af95bbd36c17cef	addColumn tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_CLIENT_SESSION		\N	4.25.1	\N	\N	2862037658
25.0.0-28265-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2024-08-05 12:47:26.816624	127	EXECUTED	9:3e96709818458ae49f3c679ae58d263a	createIndex indexName=IDX_OFFLINE_USS_BY_LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION		\N	4.25.1	\N	\N	2862037658
25.0.0-28265-index-cleanup	keycloak	META-INF/jpa-changelog-25.0.0.xml	2024-08-05 12:47:26.830066	128	EXECUTED	9:8c0cfa341a0474385b324f5c4b2dfcc1	dropIndex indexName=IDX_OFFLINE_USS_CREATEDON, tableName=OFFLINE_USER_SESSION; dropIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION; dropIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION; dropIndex ...		\N	4.25.1	\N	\N	2862037658
25.0.0-28265-index-2-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2024-08-05 12:47:26.836474	129	MARK_RAN	9:b7ef76036d3126bb83c2423bf4d449d6	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.25.1	\N	\N	2862037658
25.0.0-28265-index-2-not-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2024-08-05 12:47:26.855588	130	EXECUTED	9:23396cf51ab8bc1ae6f0cac7f9f6fcf7	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.25.1	\N	\N	2862037658
25.0.0-org	keycloak	META-INF/jpa-changelog-25.0.0.xml	2024-08-05 12:47:26.916718	131	EXECUTED	9:5c859965c2c9b9c72136c360649af157	createTable tableName=ORG; addUniqueConstraint constraintName=UK_ORG_NAME, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_GROUP, tableName=ORG; createTable tableName=ORG_DOMAIN		\N	4.25.1	\N	\N	2862037658
unique-consentuser	keycloak	META-INF/jpa-changelog-25.0.0.xml	2024-08-05 12:47:26.959235	132	EXECUTED	9:5857626a2ea8767e9a6c66bf3a2cb32f	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.25.1	\N	\N	2862037658
unique-consentuser-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2024-08-05 12:47:26.963071	133	MARK_RAN	9:b79478aad5adaa1bc428e31563f55e8e	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.25.1	\N	\N	2862037658
25.0.0-28861-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2024-08-05 12:47:26.986256	134	EXECUTED	9:b9acb58ac958d9ada0fe12a5d4794ab1	createIndex indexName=IDX_PERM_TICKET_REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; createIndex indexName=IDX_PERM_TICKET_OWNER, tableName=RESOURCE_SERVER_PERM_TICKET		\N	4.25.1	\N	\N	2862037658
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
1000	f	\N	\N
\.


--
-- Data for Name: default_client_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.default_client_scope (realm_id, scope_id, default_scope) FROM stdin;
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	c914166f-3fea-4dd1-9f2f-734f44786eae	f
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	1f9ab463-80c8-439f-8b68-ae21db071a79	t
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b	t
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	6a0c390a-ffcd-4a17-84fa-49f8c008da5b	t
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6	f
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	a2926530-4de4-4142-9f5a-742378581907	f
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	551948ef-5aa4-4722-b08e-c1b1c5229ba2	t
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	32e61767-6d89-4cbc-b3e9-096b8a3ad949	t
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	2b912d83-5c2e-44fe-b54a-f72d7c0ff846	f
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	61e59ead-9860-41f5-86b0-f766aa40b10f	t
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd	t
866b2156-2043-4bd6-8375-6cff30af44a0	7a6abf2a-7d54-4ca7-8037-725a309622a3	t
866b2156-2043-4bd6-8375-6cff30af44a0	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8	t
866b2156-2043-4bd6-8375-6cff30af44a0	b0295366-0ab4-4bb8-9da7-586cfa842c92	t
866b2156-2043-4bd6-8375-6cff30af44a0	f00a2f72-cf43-4d39-a353-4fdc37d3effa	t
866b2156-2043-4bd6-8375-6cff30af44a0	43388d0a-5526-4134-8e1d-3319eb820949	t
866b2156-2043-4bd6-8375-6cff30af44a0	edbf0dc9-24d0-471e-ab26-a740cc91bb9c	t
866b2156-2043-4bd6-8375-6cff30af44a0	fe70e32a-bc64-4c88-8533-b86c2a3bdba0	f
866b2156-2043-4bd6-8375-6cff30af44a0	b3ca80e9-4966-4d3c-a5b7-c46ef5de0826	f
866b2156-2043-4bd6-8375-6cff30af44a0	19a9cc74-7a24-4000-acea-2952b7b055ca	f
866b2156-2043-4bd6-8375-6cff30af44a0	bbc3bdaf-213d-4365-a42c-40040a44d8b0	f
866b2156-2043-4bd6-8375-6cff30af44a0	2d96c814-8a3d-45c1-be46-05be9fc4f34d	t
\.


--
-- Data for Name: event_entity; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.event_entity (id, client_id, details_json, error, ip_address, realm_id, session_id, event_time, type, user_id, details_json_long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_attribute (id, name, user_id, realm_id, storage_provider_id, value, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_consent; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_consent (id, client_id, user_id, realm_id, storage_provider_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: fed_user_consent_cl_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_consent_cl_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: fed_user_credential; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_credential (id, salt, type, created_date, user_id, realm_id, storage_provider_id, user_label, secret_data, credential_data, priority) FROM stdin;
\.


--
-- Data for Name: fed_user_group_membership; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_group_membership (group_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_required_action; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_required_action (required_action, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_role_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_role_mapping (role_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: federated_identity; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.federated_identity (identity_provider, realm_id, federated_user_id, federated_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: federated_user; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.federated_user (id, storage_provider_id, realm_id) FROM stdin;
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	<< Flyway Baseline >>	BASELINE	<< Flyway Baseline >>	\N	null	2024-08-07 14:55:45.021617	0	t
2	2	create stories table	SQL	V2__create_stories_table.sql	1193617671	admin	2024-08-07 14:55:45.124709	32	t
3	3	create history table	SQL	V3__create_history_table.sql	-253879730	admin	2024-08-07 14:55:45.194229	36	t
\.


--
-- Data for Name: frames; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.frames (id, story_id, title, text, text_color, picture_url, visible_button_or_none, button_text, button_text_color, button_background_color, button_url, gradient, approved) FROM stdin;
52a99cbb-b7f0-41a9-86e3-374fccd85155	1	asdas	dasdasd	#000	/site/share/htdoc/_files/skins/mobws_story/absolutbank/ALL PLATFORMS/1_52a99cbb-b7f0-41a9-86e3-374fccd85155.jpg	NONE		#000	#fff		EMPTY	\N
ec42f515-8443-4ace-813c-e59d3c43a5b3	\N	asd	asdasd	#000	/site/share/htdoc/_files/skins/mobws_story/absolutbank/ALL PLATFORMS/6_ec42f515-8443-4ace-813c-e59d3c43a5b3.jpg	NONE		#000	#fff		EMPTY	\N
2c6add10-6b6f-4311-aba1-159e7fa0f61e	9	sadasda	sdasdasdasd	#000	/site/share/htdoc/_files/skins/mobws_story/absolutbank/ALL PLATFORMS/9_2c6add10-6b6f-4311-aba1-159e7fa0f61e.jpg	NONE		#000	#fff		EMPTY	\N
6a7f91ca-dfc1-473b-b411-7b9688212bd2	10	asdas	dasdasdad	#000	/site/share/htdoc/_files/skins/mobws_story/absolutbank/ALL PLATFORMS/10_6a7f91ca-dfc1-473b-b411-7b9688212bd2.jpg	NONE		#000	#fff		EMPTY	\N
631dfd3d-f110-4a7e-8a3c-440c17e32f15	11	asdasdasd	asdasd	#000	/site/share/htdoc/_files/skins/mobws_story/absolutbank/ALL PLATFORMS/11_631dfd3d-f110-4a7e-8a3c-440c17e32f15.jpg	NONE		#000	#fff		EMPTY	\N
\.


--
-- Data for Name: group_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.group_attribute (id, name, value, group_id) FROM stdin;
\.


--
-- Data for Name: group_role_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.group_role_mapping (role_id, group_id) FROM stdin;
\.


--
-- Data for Name: history; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.history (id, component_type, operation_type, bank, platform, status, component_id, "time", user_name) FROM stdin;
2	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
3	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
4	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
5	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
6	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
7	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
8	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
9	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
10	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
12	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
13	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
14	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
15	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
16	STORIES	\N	ALL PLATFORMS	\N	\N	\N	2024-08-07	admin
17	STORIES	\N	ALL PLATFORMS	\N	\N	\N	2024-08-07	admin
18	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
19	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
20	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
21	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
22	STORIES	\N	ALL PLATFORMS	\N	\N	\N	2024-08-07	admin
23	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
24	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
25	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
26	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
27	STORIES	Create	\N	\N	\N	\N	2024-08-07	admin
28	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
29	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
30	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
31	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
33	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
34	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
35	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
36	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
37	STORIES	\N	ALL PLATFORMS	\N	\N	\N	2024-08-07	admin
38	STORIES	Create	\N	\N	\N	\N	2024-08-07	admin
39	STORIES	\N	\N	\N	\N	\N	2024-08-07	\N
40	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
41	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
42	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
43	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
44	STORIES	Delete	ALL PLATFORMS	\N	\N	9	2024-08-07	admin
45	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
46	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
47	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
48	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
49	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
50	STORIES	Delete	ALL PLATFORMS	\N	\N	9	2024-08-07	admin
51	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
52	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
53	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
54	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
55	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
56	STORIES	Create	\N	\N	\N	\N	2024-08-07	admin
57	STORIES	Create	\N	\N	\N	\N	2024-08-07	admin
58	STORIES	Create	\N	\N	\N	\N	2024-08-07	admin
59	STORIES	Delete	ALL PLATFORMS	\N	\N	11	2024-08-07	admin
60	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
61	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
62	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
63	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
64	STORIES	\N	\N	\N	\N	\N	2024-08-07	\N
65	STORIES	Delete	ALL PLATFORMS	\N	\N	10	2024-08-07	admin
66	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
67	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
68	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
69	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
70	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
71	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
72	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
73	STORIES	\N	\N	\N	\N	\N	2024-08-07	admin
\.


--
-- Data for Name: identity_provider; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.identity_provider (internal_id, enabled, provider_alias, provider_id, store_token, authenticate_by_default, realm_id, add_token_role, trust_email, first_broker_login_flow_id, post_broker_login_flow_id, provider_display_name, link_only) FROM stdin;
\.


--
-- Data for Name: identity_provider_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.identity_provider_config (identity_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: identity_provider_mapper; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.identity_provider_mapper (id, name, idp_alias, idp_mapper_name, realm_id) FROM stdin;
\.


--
-- Data for Name: idp_mapper_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.idp_mapper_config (idp_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: keycloak_group; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.keycloak_group (id, name, parent_group, realm_id) FROM stdin;
\.


--
-- Data for Name: keycloak_role; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.keycloak_role (id, client_realm_constraint, client_role, description, name, realm_id, client, realm) FROM stdin;
b0474fc5-0f15-4f4f-b0be-55b37a4f64bc	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f	${role_default-roles}	default-roles-master	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N	\N
119defff-e507-42b8-8a38-ff9a18f61179	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f	${role_admin}	admin	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N	\N
8ffe4731-e9b7-423d-bb37-aa04aa1e5be1	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f	${role_create-realm}	create-realm	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N	\N
2e739d13-1803-40a7-8c9f-bbf5d931d5e2	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_create-client}	create-client	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
1fcf8f74-d007-42e0-872c-065a3e591182	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_view-realm}	view-realm	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
574f2784-b0f7-443a-ac89-2434ca2b4f4e	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_view-users}	view-users	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
eaaa9326-d485-4299-875f-04464511be38	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_view-clients}	view-clients	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
0d0177e7-c758-4e96-92f4-82358b4d992a	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_view-events}	view-events	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
5bfb4ad7-562f-4848-988b-dd0c6e9ba472	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_view-identity-providers}	view-identity-providers	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
a97e3e8f-ae70-4c95-a3a8-c5d8031cf56d	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_view-authorization}	view-authorization	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
b6e6dd96-b2c6-46fe-a0a6-057f65ba9f3b	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_manage-realm}	manage-realm	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
bae08630-d284-469e-b548-5cae3ac7283f	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_manage-users}	manage-users	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
8fb5cbe7-1876-422f-a6e1-042987f991ad	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_manage-clients}	manage-clients	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
acdc6497-ea9c-408e-b7c7-b22e65c810b2	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_manage-events}	manage-events	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
b16f786e-5aad-4803-b82c-59e3de53fd58	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_manage-identity-providers}	manage-identity-providers	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
64d99976-a8f7-4a4c-9add-f8f0305a1c64	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_manage-authorization}	manage-authorization	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
e48ac78f-6ac5-4f13-b186-7264ac86d8df	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_query-users}	query-users	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
cccb616d-b21d-41e4-b41f-a800baa94803	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_query-clients}	query-clients	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
93dd3cdb-1e6d-49ff-8cf6-c2144a9c24de	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_query-realms}	query-realms	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
b1f8e283-d60a-47e2-8b5c-d56463d6c473	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_query-groups}	query-groups	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
b535b1c7-09ce-405d-84c5-008eb0f490a7	b13f0d0f-58f7-4834-b608-a1ffb579c343	t	${role_view-profile}	view-profile	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	b13f0d0f-58f7-4834-b608-a1ffb579c343	\N
1eb07f91-217b-4e84-9181-51fe8fb73641	b13f0d0f-58f7-4834-b608-a1ffb579c343	t	${role_manage-account}	manage-account	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	b13f0d0f-58f7-4834-b608-a1ffb579c343	\N
2c54c3e4-a247-457c-9d08-6abf24b412d3	b13f0d0f-58f7-4834-b608-a1ffb579c343	t	${role_manage-account-links}	manage-account-links	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	b13f0d0f-58f7-4834-b608-a1ffb579c343	\N
90affb22-3ff7-4e6d-84e6-9d85c15f6d74	b13f0d0f-58f7-4834-b608-a1ffb579c343	t	${role_view-applications}	view-applications	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	b13f0d0f-58f7-4834-b608-a1ffb579c343	\N
141c8449-c970-4b6a-bd89-03f3c21f43fd	b13f0d0f-58f7-4834-b608-a1ffb579c343	t	${role_view-consent}	view-consent	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	b13f0d0f-58f7-4834-b608-a1ffb579c343	\N
cd5772ec-0c02-4d34-97d7-ea268c1d0f39	b13f0d0f-58f7-4834-b608-a1ffb579c343	t	${role_manage-consent}	manage-consent	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	b13f0d0f-58f7-4834-b608-a1ffb579c343	\N
345adc5b-1e68-4c37-bd01-67ba4b871302	b13f0d0f-58f7-4834-b608-a1ffb579c343	t	${role_view-groups}	view-groups	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	b13f0d0f-58f7-4834-b608-a1ffb579c343	\N
bc6dc2fd-1ddd-485b-9565-8080406c1108	b13f0d0f-58f7-4834-b608-a1ffb579c343	t	${role_delete-account}	delete-account	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	b13f0d0f-58f7-4834-b608-a1ffb579c343	\N
28b60476-2fd5-4a78-9d09-9add4730d709	ac6afdec-f375-4494-8335-a581c2d581a7	t	${role_read-token}	read-token	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	ac6afdec-f375-4494-8335-a581c2d581a7	\N
9e9c0a9e-7af6-47dd-9b5e-c5964ec35542	52a54993-a1a5-461a-bfe3-f27e68d50632	t	${role_impersonation}	impersonation	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	52a54993-a1a5-461a-bfe3-f27e68d50632	\N
1be4c1a9-a090-4243-ad3b-3bf460704645	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f	${role_offline-access}	offline_access	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N	\N
a6c508d9-63ef-40f3-991d-8fd703e2d6f4	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f	${role_uma_authorization}	uma_authorization	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	\N	\N
62a2688e-e798-41b6-b400-f8eef9a15e61	496a27a9-032c-4976-addd-ee39086ad486	t	${role_create-client}	create-client	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
98271fb0-6880-45e9-b84a-5b89a67f5eef	496a27a9-032c-4976-addd-ee39086ad486	t	${role_view-realm}	view-realm	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
4102c133-7ed5-45ab-bdfc-99c1fc3c3f1a	496a27a9-032c-4976-addd-ee39086ad486	t	${role_view-users}	view-users	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
635343c5-d32e-440c-8650-c7371a4411d0	496a27a9-032c-4976-addd-ee39086ad486	t	${role_view-clients}	view-clients	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
b096ddff-f77f-46b9-b20d-5cfebda40798	496a27a9-032c-4976-addd-ee39086ad486	t	${role_view-events}	view-events	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
cdee5726-97f6-428e-9b57-cbb1a0092abc	496a27a9-032c-4976-addd-ee39086ad486	t	${role_view-identity-providers}	view-identity-providers	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
e4226c02-88df-4bd5-8247-659b6e81752b	496a27a9-032c-4976-addd-ee39086ad486	t	${role_view-authorization}	view-authorization	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
3d802def-bc0e-479f-877f-1bd500ea0304	496a27a9-032c-4976-addd-ee39086ad486	t	${role_manage-realm}	manage-realm	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
a20c460e-e695-474e-af24-8febbea37ecb	496a27a9-032c-4976-addd-ee39086ad486	t	${role_manage-users}	manage-users	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
aac46f5a-35e0-496d-970f-64efb6647a96	496a27a9-032c-4976-addd-ee39086ad486	t	${role_manage-clients}	manage-clients	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
eda8ec5a-8302-462c-9959-62fe66d2526d	496a27a9-032c-4976-addd-ee39086ad486	t	${role_manage-events}	manage-events	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
e84d24a4-3673-4791-8a1b-0d3892051ab1	496a27a9-032c-4976-addd-ee39086ad486	t	${role_manage-identity-providers}	manage-identity-providers	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
e14f7e7e-d05d-4065-871f-10a6fbb42754	496a27a9-032c-4976-addd-ee39086ad486	t	${role_manage-authorization}	manage-authorization	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
7e676520-9822-418e-be62-1b9e2e9ad6a6	496a27a9-032c-4976-addd-ee39086ad486	t	${role_query-users}	query-users	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
ba94f1c0-e78e-44fa-be1b-22a60a81f2de	496a27a9-032c-4976-addd-ee39086ad486	t	${role_query-clients}	query-clients	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
eec153b1-07fa-4882-9984-aefaf7262572	496a27a9-032c-4976-addd-ee39086ad486	t	${role_query-realms}	query-realms	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
03e8efd7-e17c-46ba-aed7-52cea4aa1e41	496a27a9-032c-4976-addd-ee39086ad486	t	${role_query-groups}	query-groups	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
3488f2ca-1dbc-40db-9674-a41edb5245cb	496a27a9-032c-4976-addd-ee39086ad486	t	${role_impersonation}	impersonation	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	496a27a9-032c-4976-addd-ee39086ad486	\N
c2c84cc6-e077-4f7f-8328-1d36514b9d29	866b2156-2043-4bd6-8375-6cff30af44a0	f	${role_default-roles}	default-roles-conent-maker	866b2156-2043-4bd6-8375-6cff30af44a0	\N	\N
fd63f58f-5a2b-413e-a1eb-89403ff94508	866b2156-2043-4bd6-8375-6cff30af44a0	f	${role_offline-access}	offline_access	866b2156-2043-4bd6-8375-6cff30af44a0	\N	\N
1b790592-5715-474f-bf3e-236b957642bf	866b2156-2043-4bd6-8375-6cff30af44a0	f	${role_uma_authorization}	uma_authorization	866b2156-2043-4bd6-8375-6cff30af44a0	\N	\N
47b79b6e-9772-4e13-9b66-4e9b0795e543	866b2156-2043-4bd6-8375-6cff30af44a0	f		ADMIN	866b2156-2043-4bd6-8375-6cff30af44a0	\N	\N
dba9c687-dd42-46e5-8251-ba232130387f	866b2156-2043-4bd6-8375-6cff30af44a0	f		USER	866b2156-2043-4bd6-8375-6cff30af44a0	\N	\N
640c6d85-9e75-4bcc-9888-12fce69bb801	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_view-users}	view-users	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
f758bfa5-57fb-4cce-9cde-f5adf07d8a1c	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_view-identity-providers}	view-identity-providers	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
7ea041ef-ed69-41b7-a42c-6fc9a9b1b314	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_manage-identity-providers}	manage-identity-providers	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
c834e0c4-f2f4-4dbc-8763-39c069195f6a	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_view-events}	view-events	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
4ed58a82-36fc-460f-aff9-01a5186e0b07	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_manage-events}	manage-events	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
4267d9e9-1df6-41a5-b7dc-0e4a7d5a6a38	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_query-users}	query-users	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
2597a4ca-aa75-434b-a94f-d2061fc608e4	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_realm-admin}	realm-admin	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
c1ff597a-7b25-4590-ada0-39cbe23c1d41	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_impersonation}	impersonation	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
c71fe027-139d-497a-9ff3-74f97c2f296f	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_manage-authorization}	manage-authorization	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
ee4e4b4d-18f3-4b29-a943-d00efa62c3ef	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_manage-users}	manage-users	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
d1894fe9-2776-4281-8524-3c0af7b2e16b	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_view-authorization}	view-authorization	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
31c9bea2-0956-4699-b3cf-7abb3c967301	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_query-realms}	query-realms	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
fff1221c-a306-4554-a821-8e5ce0cbcdb7	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_create-client}	create-client	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
ee48222f-cc73-4045-ad6e-d72491cfcecd	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_view-realm}	view-realm	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
6e726a93-c9af-4ddb-8a74-fe7f7908f9d9	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_query-clients}	query-clients	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
d4fbf3f4-3961-4f18-899a-b186f66b7ec0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_view-clients}	view-clients	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
389c1215-255e-4b52-a7f4-28012e5a45ee	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_manage-clients}	manage-clients	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
6f75863b-c52b-417f-b8af-27abcee077a2	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_manage-realm}	manage-realm	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
3302da8e-f372-40a7-be29-951344ef33a7	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	t	${role_query-groups}	query-groups	866b2156-2043-4bd6-8375-6cff30af44a0	6cf5204c-9daa-4ba8-ad21-48a19fbfff09	\N
94b539d1-4643-4a3d-a2c9-c721c271ec97	22cd5734-9234-4009-8349-809a8168d548	t	\N	uma_protection	866b2156-2043-4bd6-8375-6cff30af44a0	22cd5734-9234-4009-8349-809a8168d548	\N
2b00c7a1-0961-4b38-ac9a-3be794211c12	22cd5734-9234-4009-8349-809a8168d548	t		USER	866b2156-2043-4bd6-8375-6cff30af44a0	22cd5734-9234-4009-8349-809a8168d548	\N
47040490-7cd5-4bb4-96dd-8e284781cff0	22cd5734-9234-4009-8349-809a8168d548	t		ADMIN	866b2156-2043-4bd6-8375-6cff30af44a0	22cd5734-9234-4009-8349-809a8168d548	\N
dfaa166d-c379-4630-aed9-afc83e7a449b	949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	t		ADMIN	866b2156-2043-4bd6-8375-6cff30af44a0	949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	\N
9a148790-48aa-4d42-ab72-129cd7dcc5d4	a685d671-6d25-440d-a108-2678b8b9c297	t	${role_read-token}	read-token	866b2156-2043-4bd6-8375-6cff30af44a0	a685d671-6d25-440d-a108-2678b8b9c297	\N
3b6aa674-2632-43a3-88f5-32ddb98c3cfb	b46397d7-5fed-4d25-be0d-2957ef5534ec	t	${role_delete-account}	delete-account	866b2156-2043-4bd6-8375-6cff30af44a0	b46397d7-5fed-4d25-be0d-2957ef5534ec	\N
c7f792b7-5efd-45b0-85d6-f2328dc4d0c1	b46397d7-5fed-4d25-be0d-2957ef5534ec	t	${role_manage-account-links}	manage-account-links	866b2156-2043-4bd6-8375-6cff30af44a0	b46397d7-5fed-4d25-be0d-2957ef5534ec	\N
e36e2123-c2c6-4764-9841-b7e8ac0d4aee	b46397d7-5fed-4d25-be0d-2957ef5534ec	t	${role_view-consent}	view-consent	866b2156-2043-4bd6-8375-6cff30af44a0	b46397d7-5fed-4d25-be0d-2957ef5534ec	\N
3cb1de20-09f4-4261-bd6d-5bbe5f592859	b46397d7-5fed-4d25-be0d-2957ef5534ec	t	${role_manage-consent}	manage-consent	866b2156-2043-4bd6-8375-6cff30af44a0	b46397d7-5fed-4d25-be0d-2957ef5534ec	\N
0f598600-c670-4e80-b32e-723ebe42e33e	b46397d7-5fed-4d25-be0d-2957ef5534ec	t	${role_view-applications}	view-applications	866b2156-2043-4bd6-8375-6cff30af44a0	b46397d7-5fed-4d25-be0d-2957ef5534ec	\N
5ca872f8-9fbf-4f9f-9e80-da2af094d2f9	b46397d7-5fed-4d25-be0d-2957ef5534ec	t	${role_view-groups}	view-groups	866b2156-2043-4bd6-8375-6cff30af44a0	b46397d7-5fed-4d25-be0d-2957ef5534ec	\N
4ac8416f-2fcb-427e-98f9-f7e9e310d208	b46397d7-5fed-4d25-be0d-2957ef5534ec	t	${role_manage-account}	manage-account	866b2156-2043-4bd6-8375-6cff30af44a0	b46397d7-5fed-4d25-be0d-2957ef5534ec	\N
8d4298f0-399e-4cb1-8a4b-910d17dd958c	b46397d7-5fed-4d25-be0d-2957ef5534ec	t	${role_view-profile}	view-profile	866b2156-2043-4bd6-8375-6cff30af44a0	b46397d7-5fed-4d25-be0d-2957ef5534ec	\N
\.


--
-- Data for Name: migration_model; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.migration_model (id, version, update_time) FROM stdin;
l6j9p	25.0.2	1722862047
\.


--
-- Data for Name: offline_client_session; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.offline_client_session (user_session_id, client_id, offline_flag, "timestamp", data, client_storage_provider, external_client_id, version) FROM stdin;
\.


--
-- Data for Name: offline_user_session; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.offline_user_session (user_session_id, user_id, realm_id, created_on, offline_flag, data, last_session_refresh, broker_session_id, version) FROM stdin;
\.


--
-- Data for Name: org; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.org (id, enabled, realm_id, group_id, name, description) FROM stdin;
\.


--
-- Data for Name: org_domain; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.org_domain (id, name, verified, org_id) FROM stdin;
\.


--
-- Data for Name: policy_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.policy_config (policy_id, name, value) FROM stdin;
\.


--
-- Data for Name: protocol_mapper; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.protocol_mapper (id, name, protocol, protocol_mapper_name, client_id, client_scope_id) FROM stdin;
c15ce8fa-e007-4067-9e4e-772a9e945bc2	audience resolve	openid-connect	oidc-audience-resolve-mapper	6be14e92-ab08-4735-a7c3-9077efc21122	\N
7c1d1dd6-f248-4800-abe4-d91a41b7a6a9	locale	openid-connect	oidc-usermodel-attribute-mapper	42284954-6f22-48fe-b901-e61519350f85	\N
05a8c4cd-dd65-4d2c-a0e6-fc912c6eafa8	role list	saml	saml-role-list-mapper	\N	1f9ab463-80c8-439f-8b68-ae21db071a79
20967650-ef70-4b26-9f55-d46d336c1f83	full name	openid-connect	oidc-full-name-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
1c73253c-2279-4b99-bc14-73f537fec88b	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
9ff2f893-98d5-43d6-b6f7-f1a10b9821cb	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
738c4920-6cb5-4608-b31a-99ba9dee0409	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
79bf0a94-d50e-41ec-8457-c6189722d0f1	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
5153a33c-12f1-4341-a9ef-4990164ac04f	username	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
9a06b69b-3f32-4433-b401-a72afd6a05ae	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
72af23a7-1784-44d2-9fd3-4d93d1aaa13f	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
5afcd22b-0391-444b-b6aa-6acdb33884e1	website	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
d963fe81-3f59-40f6-8a68-7e4383b81d72	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
7505e28c-2edb-4346-ac40-7eb6eb2bb994	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
4a76531d-73e7-46d1-b1d0-0b0cc977c47d	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
b5dc0c09-3205-4754-ad9f-b6f950d3ce24	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
d4895307-04c1-4c68-96fd-e4e13a1d2fe0	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	f6cb45fc-cba0-4357-92e5-bcf06b1ffd6b
5dcea190-15f5-4888-9ac7-a63a399192c2	email	openid-connect	oidc-usermodel-attribute-mapper	\N	6a0c390a-ffcd-4a17-84fa-49f8c008da5b
f176bab5-7883-45fc-a529-ceb4ebda4b25	email verified	openid-connect	oidc-usermodel-property-mapper	\N	6a0c390a-ffcd-4a17-84fa-49f8c008da5b
37ca3a79-0f51-422c-adfb-821d6e919dea	address	openid-connect	oidc-address-mapper	\N	6ef6d9c2-e615-4ee6-b9eb-7440b539a8f6
76637a1f-9121-4ba3-8c01-f5b09a6fb5a8	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	a2926530-4de4-4142-9f5a-742378581907
96131554-a4ab-457a-9115-61c346d6a526	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	a2926530-4de4-4142-9f5a-742378581907
aae08da2-4871-4222-9c1d-9a2d2799eba1	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	551948ef-5aa4-4722-b08e-c1b1c5229ba2
ee4828f8-ea28-4a00-843b-f01ab425dbe3	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	551948ef-5aa4-4722-b08e-c1b1c5229ba2
e44a8f2a-1c88-467d-9de4-55a6736213da	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	551948ef-5aa4-4722-b08e-c1b1c5229ba2
78bbbe62-3549-4865-8764-8e3965f595d5	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	32e61767-6d89-4cbc-b3e9-096b8a3ad949
92731694-dfe9-42d1-81bf-9816d7deca7f	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	2b912d83-5c2e-44fe-b54a-f72d7c0ff846
c8fdff50-7a20-4029-880c-e0dc2e4f7641	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	2b912d83-5c2e-44fe-b54a-f72d7c0ff846
b1e30257-8d9a-435b-8c4a-6bb4736d0cdf	acr loa level	openid-connect	oidc-acr-mapper	\N	61e59ead-9860-41f5-86b0-f766aa40b10f
22208584-6a03-44b3-92b4-b1f1dc140d27	auth_time	openid-connect	oidc-usersessionmodel-note-mapper	\N	ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd
bbca6cdc-9c16-47af-ac84-ff11ff986e27	sub	openid-connect	oidc-sub-mapper	\N	ebe0b757-e2a3-46ad-9aaa-be64e45b2dcd
2572942c-bc91-45c4-b874-c30c6e611517	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
8a4a52f2-eadc-4bad-ac4a-9a2cc1558019	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
020fa97f-f6e4-4a13-ba51-0d820d97ef28	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
162ff72a-52d0-44e2-a9c1-16ff84351538	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
3cbfeb38-722b-4bac-90ed-20d0072fb1bd	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
35aa9efa-9eee-458e-abeb-c8e1e8b85d1d	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
42f95a46-4e4b-4120-b151-89f9095458eb	username	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
79301dac-5068-4d66-acb3-42634edaa245	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
f98d5824-6a7c-4b8a-8a86-28cb5df2e158	full name	openid-connect	oidc-full-name-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
18a1080e-813f-44f1-b480-4d0c33e9da89	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
ee3032bc-5b3b-41f5-9077-282708083b28	website	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
7eaf12bf-1939-43b9-8946-cec58fd1ec4d	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
3581bc25-7537-4654-8819-c65a6e17cb88	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
ec6b93e1-e8f8-4160-a20c-e9f5582fc52e	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	32d4d39e-c0fa-4fe8-9707-3da1a2f8c6f8
792ad651-f933-4468-aa01-c11f242e75e7	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	43388d0a-5526-4134-8e1d-3319eb820949
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	address	openid-connect	oidc-address-mapper	\N	b3ca80e9-4966-4d3c-a5b7-c46ef5de0826
6ed5ec73-da5d-4066-a4af-76142a87d5a4	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	f00a2f72-cf43-4d39-a353-4fdc37d3effa
41380b79-3eb8-471a-be4a-18f3b113faae	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	f00a2f72-cf43-4d39-a353-4fdc37d3effa
0ef091af-7544-445c-81f4-48d6fa03eb89	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	f00a2f72-cf43-4d39-a353-4fdc37d3effa
20eac4de-d1b0-4fa5-9550-9da7c9adbbc7	role list	saml	saml-role-list-mapper	\N	7a6abf2a-7d54-4ca7-8037-725a309622a3
d51a6750-6155-4233-a9a8-06e3bbb033bf	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	bbc3bdaf-213d-4365-a42c-40040a44d8b0
bc4ad371-b3a1-4a4b-9eda-738a32c61b48	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	bbc3bdaf-213d-4365-a42c-40040a44d8b0
a776901e-14e5-4c12-be67-64c7dfdbbe1a	acr loa level	openid-connect	oidc-acr-mapper	\N	edbf0dc9-24d0-471e-ab26-a740cc91bb9c
09f0bfb5-6283-4a95-9869-c1e37e2d1e46	email verified	openid-connect	oidc-usermodel-property-mapper	\N	b0295366-0ab4-4bb8-9da7-586cfa842c92
3d1204f4-1dfd-4242-8bad-81ab60eab378	email	openid-connect	oidc-usermodel-attribute-mapper	\N	b0295366-0ab4-4bb8-9da7-586cfa842c92
eaebfe93-b362-48a6-ad98-9482105e198a	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	19a9cc74-7a24-4000-acea-2952b7b055ca
30207414-3f33-4d22-b4d1-2a274035cd0e	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	19a9cc74-7a24-4000-acea-2952b7b055ca
f9763113-f5b5-47be-b4db-424d2874a42a	locale	openid-connect	oidc-usermodel-attribute-mapper	f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	\N
045a7689-c250-4126-ae09-3a5db53a6a60	auth_time	openid-connect	oidc-usersessionmodel-note-mapper	\N	2d96c814-8a3d-45c1-be46-05be9fc4f34d
0c965fb9-4b04-4d44-95d8-f76f3068c434	sub	openid-connect	oidc-sub-mapper	\N	2d96c814-8a3d-45c1-be46-05be9fc4f34d
adaf7b29-d596-437c-80ae-cb090c1f8f24	Client Host	openid-connect	oidc-usersessionmodel-note-mapper	22cd5734-9234-4009-8349-809a8168d548	\N
c461e608-ddfa-4948-a145-fb2337438a92	Client ID	openid-connect	oidc-usersessionmodel-note-mapper	22cd5734-9234-4009-8349-809a8168d548	\N
e6983fb8-cece-4662-90b4-77ed33eddcd3	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	22cd5734-9234-4009-8349-809a8168d548	\N
187d850f-068b-441f-bce3-69bb4d44bd3a	Client IP Address	openid-connect	oidc-usersessionmodel-note-mapper	22cd5734-9234-4009-8349-809a8168d548	\N
3eb5fa16-4cea-49b3-a77e-cb1d1fa285d0	audience resolve	openid-connect	oidc-audience-resolve-mapper	a8d9a0dc-08c9-486f-bbb3-8522b16cb578	\N
\.


--
-- Data for Name: protocol_mapper_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.protocol_mapper_config (protocol_mapper_id, value, name) FROM stdin;
7c1d1dd6-f248-4800-abe4-d91a41b7a6a9	true	introspection.token.claim
7c1d1dd6-f248-4800-abe4-d91a41b7a6a9	true	userinfo.token.claim
7c1d1dd6-f248-4800-abe4-d91a41b7a6a9	locale	user.attribute
7c1d1dd6-f248-4800-abe4-d91a41b7a6a9	true	id.token.claim
7c1d1dd6-f248-4800-abe4-d91a41b7a6a9	true	access.token.claim
7c1d1dd6-f248-4800-abe4-d91a41b7a6a9	locale	claim.name
7c1d1dd6-f248-4800-abe4-d91a41b7a6a9	String	jsonType.label
05a8c4cd-dd65-4d2c-a0e6-fc912c6eafa8	false	single
05a8c4cd-dd65-4d2c-a0e6-fc912c6eafa8	Basic	attribute.nameformat
05a8c4cd-dd65-4d2c-a0e6-fc912c6eafa8	Role	attribute.name
1c73253c-2279-4b99-bc14-73f537fec88b	true	introspection.token.claim
1c73253c-2279-4b99-bc14-73f537fec88b	true	userinfo.token.claim
1c73253c-2279-4b99-bc14-73f537fec88b	lastName	user.attribute
1c73253c-2279-4b99-bc14-73f537fec88b	true	id.token.claim
1c73253c-2279-4b99-bc14-73f537fec88b	true	access.token.claim
1c73253c-2279-4b99-bc14-73f537fec88b	family_name	claim.name
1c73253c-2279-4b99-bc14-73f537fec88b	String	jsonType.label
20967650-ef70-4b26-9f55-d46d336c1f83	true	introspection.token.claim
20967650-ef70-4b26-9f55-d46d336c1f83	true	userinfo.token.claim
20967650-ef70-4b26-9f55-d46d336c1f83	true	id.token.claim
20967650-ef70-4b26-9f55-d46d336c1f83	true	access.token.claim
4a76531d-73e7-46d1-b1d0-0b0cc977c47d	true	introspection.token.claim
4a76531d-73e7-46d1-b1d0-0b0cc977c47d	true	userinfo.token.claim
4a76531d-73e7-46d1-b1d0-0b0cc977c47d	zoneinfo	user.attribute
4a76531d-73e7-46d1-b1d0-0b0cc977c47d	true	id.token.claim
4a76531d-73e7-46d1-b1d0-0b0cc977c47d	true	access.token.claim
4a76531d-73e7-46d1-b1d0-0b0cc977c47d	zoneinfo	claim.name
4a76531d-73e7-46d1-b1d0-0b0cc977c47d	String	jsonType.label
5153a33c-12f1-4341-a9ef-4990164ac04f	true	introspection.token.claim
5153a33c-12f1-4341-a9ef-4990164ac04f	true	userinfo.token.claim
5153a33c-12f1-4341-a9ef-4990164ac04f	username	user.attribute
5153a33c-12f1-4341-a9ef-4990164ac04f	true	id.token.claim
5153a33c-12f1-4341-a9ef-4990164ac04f	true	access.token.claim
5153a33c-12f1-4341-a9ef-4990164ac04f	preferred_username	claim.name
5153a33c-12f1-4341-a9ef-4990164ac04f	String	jsonType.label
5afcd22b-0391-444b-b6aa-6acdb33884e1	true	introspection.token.claim
5afcd22b-0391-444b-b6aa-6acdb33884e1	true	userinfo.token.claim
5afcd22b-0391-444b-b6aa-6acdb33884e1	website	user.attribute
5afcd22b-0391-444b-b6aa-6acdb33884e1	true	id.token.claim
5afcd22b-0391-444b-b6aa-6acdb33884e1	true	access.token.claim
5afcd22b-0391-444b-b6aa-6acdb33884e1	website	claim.name
5afcd22b-0391-444b-b6aa-6acdb33884e1	String	jsonType.label
72af23a7-1784-44d2-9fd3-4d93d1aaa13f	true	introspection.token.claim
72af23a7-1784-44d2-9fd3-4d93d1aaa13f	true	userinfo.token.claim
72af23a7-1784-44d2-9fd3-4d93d1aaa13f	picture	user.attribute
72af23a7-1784-44d2-9fd3-4d93d1aaa13f	true	id.token.claim
72af23a7-1784-44d2-9fd3-4d93d1aaa13f	true	access.token.claim
72af23a7-1784-44d2-9fd3-4d93d1aaa13f	picture	claim.name
72af23a7-1784-44d2-9fd3-4d93d1aaa13f	String	jsonType.label
738c4920-6cb5-4608-b31a-99ba9dee0409	true	introspection.token.claim
738c4920-6cb5-4608-b31a-99ba9dee0409	true	userinfo.token.claim
738c4920-6cb5-4608-b31a-99ba9dee0409	middleName	user.attribute
738c4920-6cb5-4608-b31a-99ba9dee0409	true	id.token.claim
738c4920-6cb5-4608-b31a-99ba9dee0409	true	access.token.claim
738c4920-6cb5-4608-b31a-99ba9dee0409	middle_name	claim.name
738c4920-6cb5-4608-b31a-99ba9dee0409	String	jsonType.label
7505e28c-2edb-4346-ac40-7eb6eb2bb994	true	introspection.token.claim
7505e28c-2edb-4346-ac40-7eb6eb2bb994	true	userinfo.token.claim
7505e28c-2edb-4346-ac40-7eb6eb2bb994	birthdate	user.attribute
7505e28c-2edb-4346-ac40-7eb6eb2bb994	true	id.token.claim
7505e28c-2edb-4346-ac40-7eb6eb2bb994	true	access.token.claim
7505e28c-2edb-4346-ac40-7eb6eb2bb994	birthdate	claim.name
7505e28c-2edb-4346-ac40-7eb6eb2bb994	String	jsonType.label
79bf0a94-d50e-41ec-8457-c6189722d0f1	true	introspection.token.claim
79bf0a94-d50e-41ec-8457-c6189722d0f1	true	userinfo.token.claim
79bf0a94-d50e-41ec-8457-c6189722d0f1	nickname	user.attribute
79bf0a94-d50e-41ec-8457-c6189722d0f1	true	id.token.claim
79bf0a94-d50e-41ec-8457-c6189722d0f1	true	access.token.claim
79bf0a94-d50e-41ec-8457-c6189722d0f1	nickname	claim.name
79bf0a94-d50e-41ec-8457-c6189722d0f1	String	jsonType.label
9a06b69b-3f32-4433-b401-a72afd6a05ae	true	introspection.token.claim
9a06b69b-3f32-4433-b401-a72afd6a05ae	true	userinfo.token.claim
9a06b69b-3f32-4433-b401-a72afd6a05ae	profile	user.attribute
9a06b69b-3f32-4433-b401-a72afd6a05ae	true	id.token.claim
9a06b69b-3f32-4433-b401-a72afd6a05ae	true	access.token.claim
9a06b69b-3f32-4433-b401-a72afd6a05ae	profile	claim.name
9a06b69b-3f32-4433-b401-a72afd6a05ae	String	jsonType.label
9ff2f893-98d5-43d6-b6f7-f1a10b9821cb	true	introspection.token.claim
9ff2f893-98d5-43d6-b6f7-f1a10b9821cb	true	userinfo.token.claim
9ff2f893-98d5-43d6-b6f7-f1a10b9821cb	firstName	user.attribute
9ff2f893-98d5-43d6-b6f7-f1a10b9821cb	true	id.token.claim
9ff2f893-98d5-43d6-b6f7-f1a10b9821cb	true	access.token.claim
9ff2f893-98d5-43d6-b6f7-f1a10b9821cb	given_name	claim.name
9ff2f893-98d5-43d6-b6f7-f1a10b9821cb	String	jsonType.label
b5dc0c09-3205-4754-ad9f-b6f950d3ce24	true	introspection.token.claim
b5dc0c09-3205-4754-ad9f-b6f950d3ce24	true	userinfo.token.claim
b5dc0c09-3205-4754-ad9f-b6f950d3ce24	locale	user.attribute
b5dc0c09-3205-4754-ad9f-b6f950d3ce24	true	id.token.claim
b5dc0c09-3205-4754-ad9f-b6f950d3ce24	true	access.token.claim
b5dc0c09-3205-4754-ad9f-b6f950d3ce24	locale	claim.name
b5dc0c09-3205-4754-ad9f-b6f950d3ce24	String	jsonType.label
d4895307-04c1-4c68-96fd-e4e13a1d2fe0	true	introspection.token.claim
d4895307-04c1-4c68-96fd-e4e13a1d2fe0	true	userinfo.token.claim
d4895307-04c1-4c68-96fd-e4e13a1d2fe0	updatedAt	user.attribute
d4895307-04c1-4c68-96fd-e4e13a1d2fe0	true	id.token.claim
d4895307-04c1-4c68-96fd-e4e13a1d2fe0	true	access.token.claim
d4895307-04c1-4c68-96fd-e4e13a1d2fe0	updated_at	claim.name
d4895307-04c1-4c68-96fd-e4e13a1d2fe0	long	jsonType.label
d963fe81-3f59-40f6-8a68-7e4383b81d72	true	introspection.token.claim
d963fe81-3f59-40f6-8a68-7e4383b81d72	true	userinfo.token.claim
d963fe81-3f59-40f6-8a68-7e4383b81d72	gender	user.attribute
d963fe81-3f59-40f6-8a68-7e4383b81d72	true	id.token.claim
d963fe81-3f59-40f6-8a68-7e4383b81d72	true	access.token.claim
d963fe81-3f59-40f6-8a68-7e4383b81d72	gender	claim.name
d963fe81-3f59-40f6-8a68-7e4383b81d72	String	jsonType.label
5dcea190-15f5-4888-9ac7-a63a399192c2	true	introspection.token.claim
5dcea190-15f5-4888-9ac7-a63a399192c2	true	userinfo.token.claim
5dcea190-15f5-4888-9ac7-a63a399192c2	email	user.attribute
5dcea190-15f5-4888-9ac7-a63a399192c2	true	id.token.claim
5dcea190-15f5-4888-9ac7-a63a399192c2	true	access.token.claim
5dcea190-15f5-4888-9ac7-a63a399192c2	email	claim.name
5dcea190-15f5-4888-9ac7-a63a399192c2	String	jsonType.label
f176bab5-7883-45fc-a529-ceb4ebda4b25	true	introspection.token.claim
f176bab5-7883-45fc-a529-ceb4ebda4b25	true	userinfo.token.claim
f176bab5-7883-45fc-a529-ceb4ebda4b25	emailVerified	user.attribute
f176bab5-7883-45fc-a529-ceb4ebda4b25	true	id.token.claim
f176bab5-7883-45fc-a529-ceb4ebda4b25	true	access.token.claim
f176bab5-7883-45fc-a529-ceb4ebda4b25	email_verified	claim.name
f176bab5-7883-45fc-a529-ceb4ebda4b25	boolean	jsonType.label
37ca3a79-0f51-422c-adfb-821d6e919dea	formatted	user.attribute.formatted
37ca3a79-0f51-422c-adfb-821d6e919dea	country	user.attribute.country
37ca3a79-0f51-422c-adfb-821d6e919dea	true	introspection.token.claim
37ca3a79-0f51-422c-adfb-821d6e919dea	postal_code	user.attribute.postal_code
37ca3a79-0f51-422c-adfb-821d6e919dea	true	userinfo.token.claim
37ca3a79-0f51-422c-adfb-821d6e919dea	street	user.attribute.street
37ca3a79-0f51-422c-adfb-821d6e919dea	true	id.token.claim
37ca3a79-0f51-422c-adfb-821d6e919dea	region	user.attribute.region
37ca3a79-0f51-422c-adfb-821d6e919dea	true	access.token.claim
37ca3a79-0f51-422c-adfb-821d6e919dea	locality	user.attribute.locality
76637a1f-9121-4ba3-8c01-f5b09a6fb5a8	true	introspection.token.claim
76637a1f-9121-4ba3-8c01-f5b09a6fb5a8	true	userinfo.token.claim
76637a1f-9121-4ba3-8c01-f5b09a6fb5a8	phoneNumber	user.attribute
76637a1f-9121-4ba3-8c01-f5b09a6fb5a8	true	id.token.claim
76637a1f-9121-4ba3-8c01-f5b09a6fb5a8	true	access.token.claim
76637a1f-9121-4ba3-8c01-f5b09a6fb5a8	phone_number	claim.name
76637a1f-9121-4ba3-8c01-f5b09a6fb5a8	String	jsonType.label
96131554-a4ab-457a-9115-61c346d6a526	true	introspection.token.claim
96131554-a4ab-457a-9115-61c346d6a526	true	userinfo.token.claim
96131554-a4ab-457a-9115-61c346d6a526	phoneNumberVerified	user.attribute
96131554-a4ab-457a-9115-61c346d6a526	true	id.token.claim
96131554-a4ab-457a-9115-61c346d6a526	true	access.token.claim
96131554-a4ab-457a-9115-61c346d6a526	phone_number_verified	claim.name
96131554-a4ab-457a-9115-61c346d6a526	boolean	jsonType.label
aae08da2-4871-4222-9c1d-9a2d2799eba1	true	introspection.token.claim
aae08da2-4871-4222-9c1d-9a2d2799eba1	true	multivalued
aae08da2-4871-4222-9c1d-9a2d2799eba1	foo	user.attribute
aae08da2-4871-4222-9c1d-9a2d2799eba1	true	access.token.claim
aae08da2-4871-4222-9c1d-9a2d2799eba1	realm_access.roles	claim.name
aae08da2-4871-4222-9c1d-9a2d2799eba1	String	jsonType.label
e44a8f2a-1c88-467d-9de4-55a6736213da	true	introspection.token.claim
e44a8f2a-1c88-467d-9de4-55a6736213da	true	access.token.claim
ee4828f8-ea28-4a00-843b-f01ab425dbe3	true	introspection.token.claim
ee4828f8-ea28-4a00-843b-f01ab425dbe3	true	multivalued
ee4828f8-ea28-4a00-843b-f01ab425dbe3	foo	user.attribute
ee4828f8-ea28-4a00-843b-f01ab425dbe3	true	access.token.claim
ee4828f8-ea28-4a00-843b-f01ab425dbe3	resource_access.${client_id}.roles	claim.name
ee4828f8-ea28-4a00-843b-f01ab425dbe3	String	jsonType.label
78bbbe62-3549-4865-8764-8e3965f595d5	true	introspection.token.claim
78bbbe62-3549-4865-8764-8e3965f595d5	true	access.token.claim
92731694-dfe9-42d1-81bf-9816d7deca7f	true	introspection.token.claim
92731694-dfe9-42d1-81bf-9816d7deca7f	true	userinfo.token.claim
92731694-dfe9-42d1-81bf-9816d7deca7f	username	user.attribute
92731694-dfe9-42d1-81bf-9816d7deca7f	true	id.token.claim
92731694-dfe9-42d1-81bf-9816d7deca7f	true	access.token.claim
92731694-dfe9-42d1-81bf-9816d7deca7f	upn	claim.name
92731694-dfe9-42d1-81bf-9816d7deca7f	String	jsonType.label
c8fdff50-7a20-4029-880c-e0dc2e4f7641	true	introspection.token.claim
c8fdff50-7a20-4029-880c-e0dc2e4f7641	true	multivalued
c8fdff50-7a20-4029-880c-e0dc2e4f7641	foo	user.attribute
c8fdff50-7a20-4029-880c-e0dc2e4f7641	true	id.token.claim
c8fdff50-7a20-4029-880c-e0dc2e4f7641	true	access.token.claim
c8fdff50-7a20-4029-880c-e0dc2e4f7641	groups	claim.name
c8fdff50-7a20-4029-880c-e0dc2e4f7641	String	jsonType.label
b1e30257-8d9a-435b-8c4a-6bb4736d0cdf	true	introspection.token.claim
b1e30257-8d9a-435b-8c4a-6bb4736d0cdf	true	id.token.claim
b1e30257-8d9a-435b-8c4a-6bb4736d0cdf	true	access.token.claim
22208584-6a03-44b3-92b4-b1f1dc140d27	AUTH_TIME	user.session.note
22208584-6a03-44b3-92b4-b1f1dc140d27	true	introspection.token.claim
22208584-6a03-44b3-92b4-b1f1dc140d27	true	id.token.claim
22208584-6a03-44b3-92b4-b1f1dc140d27	true	access.token.claim
22208584-6a03-44b3-92b4-b1f1dc140d27	auth_time	claim.name
22208584-6a03-44b3-92b4-b1f1dc140d27	long	jsonType.label
bbca6cdc-9c16-47af-ac84-ff11ff986e27	true	introspection.token.claim
bbca6cdc-9c16-47af-ac84-ff11ff986e27	true	access.token.claim
020fa97f-f6e4-4a13-ba51-0d820d97ef28	true	introspection.token.claim
020fa97f-f6e4-4a13-ba51-0d820d97ef28	true	userinfo.token.claim
020fa97f-f6e4-4a13-ba51-0d820d97ef28	firstName	user.attribute
020fa97f-f6e4-4a13-ba51-0d820d97ef28	true	id.token.claim
020fa97f-f6e4-4a13-ba51-0d820d97ef28	true	access.token.claim
020fa97f-f6e4-4a13-ba51-0d820d97ef28	given_name	claim.name
020fa97f-f6e4-4a13-ba51-0d820d97ef28	String	jsonType.label
162ff72a-52d0-44e2-a9c1-16ff84351538	true	introspection.token.claim
162ff72a-52d0-44e2-a9c1-16ff84351538	true	userinfo.token.claim
162ff72a-52d0-44e2-a9c1-16ff84351538	birthdate	user.attribute
162ff72a-52d0-44e2-a9c1-16ff84351538	true	id.token.claim
162ff72a-52d0-44e2-a9c1-16ff84351538	true	access.token.claim
162ff72a-52d0-44e2-a9c1-16ff84351538	birthdate	claim.name
162ff72a-52d0-44e2-a9c1-16ff84351538	String	jsonType.label
18a1080e-813f-44f1-b480-4d0c33e9da89	true	introspection.token.claim
18a1080e-813f-44f1-b480-4d0c33e9da89	true	userinfo.token.claim
18a1080e-813f-44f1-b480-4d0c33e9da89	zoneinfo	user.attribute
18a1080e-813f-44f1-b480-4d0c33e9da89	true	id.token.claim
18a1080e-813f-44f1-b480-4d0c33e9da89	true	access.token.claim
18a1080e-813f-44f1-b480-4d0c33e9da89	zoneinfo	claim.name
18a1080e-813f-44f1-b480-4d0c33e9da89	String	jsonType.label
2572942c-bc91-45c4-b874-c30c6e611517	true	introspection.token.claim
2572942c-bc91-45c4-b874-c30c6e611517	true	userinfo.token.claim
2572942c-bc91-45c4-b874-c30c6e611517	nickname	user.attribute
2572942c-bc91-45c4-b874-c30c6e611517	true	id.token.claim
2572942c-bc91-45c4-b874-c30c6e611517	true	access.token.claim
2572942c-bc91-45c4-b874-c30c6e611517	nickname	claim.name
2572942c-bc91-45c4-b874-c30c6e611517	String	jsonType.label
3581bc25-7537-4654-8819-c65a6e17cb88	true	introspection.token.claim
3581bc25-7537-4654-8819-c65a6e17cb88	true	userinfo.token.claim
3581bc25-7537-4654-8819-c65a6e17cb88	lastName	user.attribute
3581bc25-7537-4654-8819-c65a6e17cb88	true	id.token.claim
3581bc25-7537-4654-8819-c65a6e17cb88	true	access.token.claim
3581bc25-7537-4654-8819-c65a6e17cb88	family_name	claim.name
3581bc25-7537-4654-8819-c65a6e17cb88	String	jsonType.label
35aa9efa-9eee-458e-abeb-c8e1e8b85d1d	true	introspection.token.claim
35aa9efa-9eee-458e-abeb-c8e1e8b85d1d	true	userinfo.token.claim
35aa9efa-9eee-458e-abeb-c8e1e8b85d1d	picture	user.attribute
35aa9efa-9eee-458e-abeb-c8e1e8b85d1d	true	id.token.claim
35aa9efa-9eee-458e-abeb-c8e1e8b85d1d	true	access.token.claim
35aa9efa-9eee-458e-abeb-c8e1e8b85d1d	picture	claim.name
35aa9efa-9eee-458e-abeb-c8e1e8b85d1d	String	jsonType.label
3cbfeb38-722b-4bac-90ed-20d0072fb1bd	true	introspection.token.claim
3cbfeb38-722b-4bac-90ed-20d0072fb1bd	true	userinfo.token.claim
3cbfeb38-722b-4bac-90ed-20d0072fb1bd	middleName	user.attribute
3cbfeb38-722b-4bac-90ed-20d0072fb1bd	true	id.token.claim
3cbfeb38-722b-4bac-90ed-20d0072fb1bd	true	access.token.claim
3cbfeb38-722b-4bac-90ed-20d0072fb1bd	middle_name	claim.name
3cbfeb38-722b-4bac-90ed-20d0072fb1bd	String	jsonType.label
42f95a46-4e4b-4120-b151-89f9095458eb	true	introspection.token.claim
42f95a46-4e4b-4120-b151-89f9095458eb	true	userinfo.token.claim
42f95a46-4e4b-4120-b151-89f9095458eb	username	user.attribute
42f95a46-4e4b-4120-b151-89f9095458eb	true	id.token.claim
42f95a46-4e4b-4120-b151-89f9095458eb	true	access.token.claim
42f95a46-4e4b-4120-b151-89f9095458eb	preferred_username	claim.name
42f95a46-4e4b-4120-b151-89f9095458eb	String	jsonType.label
79301dac-5068-4d66-acb3-42634edaa245	true	introspection.token.claim
79301dac-5068-4d66-acb3-42634edaa245	true	userinfo.token.claim
79301dac-5068-4d66-acb3-42634edaa245	updatedAt	user.attribute
79301dac-5068-4d66-acb3-42634edaa245	true	id.token.claim
79301dac-5068-4d66-acb3-42634edaa245	true	access.token.claim
79301dac-5068-4d66-acb3-42634edaa245	updated_at	claim.name
79301dac-5068-4d66-acb3-42634edaa245	long	jsonType.label
7eaf12bf-1939-43b9-8946-cec58fd1ec4d	true	introspection.token.claim
7eaf12bf-1939-43b9-8946-cec58fd1ec4d	true	userinfo.token.claim
7eaf12bf-1939-43b9-8946-cec58fd1ec4d	locale	user.attribute
7eaf12bf-1939-43b9-8946-cec58fd1ec4d	true	id.token.claim
7eaf12bf-1939-43b9-8946-cec58fd1ec4d	true	access.token.claim
7eaf12bf-1939-43b9-8946-cec58fd1ec4d	locale	claim.name
7eaf12bf-1939-43b9-8946-cec58fd1ec4d	String	jsonType.label
8a4a52f2-eadc-4bad-ac4a-9a2cc1558019	true	introspection.token.claim
8a4a52f2-eadc-4bad-ac4a-9a2cc1558019	true	userinfo.token.claim
8a4a52f2-eadc-4bad-ac4a-9a2cc1558019	gender	user.attribute
8a4a52f2-eadc-4bad-ac4a-9a2cc1558019	true	id.token.claim
8a4a52f2-eadc-4bad-ac4a-9a2cc1558019	true	access.token.claim
8a4a52f2-eadc-4bad-ac4a-9a2cc1558019	gender	claim.name
8a4a52f2-eadc-4bad-ac4a-9a2cc1558019	String	jsonType.label
ec6b93e1-e8f8-4160-a20c-e9f5582fc52e	true	introspection.token.claim
ec6b93e1-e8f8-4160-a20c-e9f5582fc52e	true	userinfo.token.claim
ec6b93e1-e8f8-4160-a20c-e9f5582fc52e	profile	user.attribute
ec6b93e1-e8f8-4160-a20c-e9f5582fc52e	true	id.token.claim
ec6b93e1-e8f8-4160-a20c-e9f5582fc52e	true	access.token.claim
ec6b93e1-e8f8-4160-a20c-e9f5582fc52e	profile	claim.name
ec6b93e1-e8f8-4160-a20c-e9f5582fc52e	String	jsonType.label
ee3032bc-5b3b-41f5-9077-282708083b28	true	introspection.token.claim
ee3032bc-5b3b-41f5-9077-282708083b28	true	userinfo.token.claim
ee3032bc-5b3b-41f5-9077-282708083b28	website	user.attribute
ee3032bc-5b3b-41f5-9077-282708083b28	true	id.token.claim
ee3032bc-5b3b-41f5-9077-282708083b28	true	access.token.claim
ee3032bc-5b3b-41f5-9077-282708083b28	website	claim.name
ee3032bc-5b3b-41f5-9077-282708083b28	String	jsonType.label
f98d5824-6a7c-4b8a-8a86-28cb5df2e158	true	id.token.claim
f98d5824-6a7c-4b8a-8a86-28cb5df2e158	true	introspection.token.claim
f98d5824-6a7c-4b8a-8a86-28cb5df2e158	true	access.token.claim
f98d5824-6a7c-4b8a-8a86-28cb5df2e158	true	userinfo.token.claim
792ad651-f933-4468-aa01-c11f242e75e7	true	introspection.token.claim
792ad651-f933-4468-aa01-c11f242e75e7	true	access.token.claim
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	formatted	user.attribute.formatted
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	country	user.attribute.country
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	true	introspection.token.claim
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	postal_code	user.attribute.postal_code
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	true	userinfo.token.claim
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	street	user.attribute.street
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	true	id.token.claim
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	region	user.attribute.region
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	true	access.token.claim
76b9e5b3-2ed3-41c7-83d6-b2fa0bb90940	locality	user.attribute.locality
0ef091af-7544-445c-81f4-48d6fa03eb89	foo	user.attribute
0ef091af-7544-445c-81f4-48d6fa03eb89	true	introspection.token.claim
0ef091af-7544-445c-81f4-48d6fa03eb89	true	access.token.claim
0ef091af-7544-445c-81f4-48d6fa03eb89	realm_access.roles	claim.name
0ef091af-7544-445c-81f4-48d6fa03eb89	String	jsonType.label
0ef091af-7544-445c-81f4-48d6fa03eb89	true	multivalued
41380b79-3eb8-471a-be4a-18f3b113faae	true	introspection.token.claim
41380b79-3eb8-471a-be4a-18f3b113faae	true	access.token.claim
6ed5ec73-da5d-4066-a4af-76142a87d5a4	foo	user.attribute
6ed5ec73-da5d-4066-a4af-76142a87d5a4	true	introspection.token.claim
6ed5ec73-da5d-4066-a4af-76142a87d5a4	true	access.token.claim
6ed5ec73-da5d-4066-a4af-76142a87d5a4	resource_access.${client_id}.roles	claim.name
6ed5ec73-da5d-4066-a4af-76142a87d5a4	String	jsonType.label
6ed5ec73-da5d-4066-a4af-76142a87d5a4	true	multivalued
20eac4de-d1b0-4fa5-9550-9da7c9adbbc7	false	single
20eac4de-d1b0-4fa5-9550-9da7c9adbbc7	Basic	attribute.nameformat
20eac4de-d1b0-4fa5-9550-9da7c9adbbc7	Role	attribute.name
bc4ad371-b3a1-4a4b-9eda-738a32c61b48	true	introspection.token.claim
bc4ad371-b3a1-4a4b-9eda-738a32c61b48	true	userinfo.token.claim
bc4ad371-b3a1-4a4b-9eda-738a32c61b48	username	user.attribute
bc4ad371-b3a1-4a4b-9eda-738a32c61b48	true	id.token.claim
bc4ad371-b3a1-4a4b-9eda-738a32c61b48	true	access.token.claim
bc4ad371-b3a1-4a4b-9eda-738a32c61b48	upn	claim.name
bc4ad371-b3a1-4a4b-9eda-738a32c61b48	String	jsonType.label
d51a6750-6155-4233-a9a8-06e3bbb033bf	true	introspection.token.claim
d51a6750-6155-4233-a9a8-06e3bbb033bf	true	multivalued
d51a6750-6155-4233-a9a8-06e3bbb033bf	true	userinfo.token.claim
d51a6750-6155-4233-a9a8-06e3bbb033bf	foo	user.attribute
d51a6750-6155-4233-a9a8-06e3bbb033bf	true	id.token.claim
d51a6750-6155-4233-a9a8-06e3bbb033bf	true	access.token.claim
d51a6750-6155-4233-a9a8-06e3bbb033bf	groups	claim.name
d51a6750-6155-4233-a9a8-06e3bbb033bf	String	jsonType.label
a776901e-14e5-4c12-be67-64c7dfdbbe1a	true	id.token.claim
a776901e-14e5-4c12-be67-64c7dfdbbe1a	true	introspection.token.claim
a776901e-14e5-4c12-be67-64c7dfdbbe1a	true	access.token.claim
a776901e-14e5-4c12-be67-64c7dfdbbe1a	true	userinfo.token.claim
09f0bfb5-6283-4a95-9869-c1e37e2d1e46	true	introspection.token.claim
09f0bfb5-6283-4a95-9869-c1e37e2d1e46	true	userinfo.token.claim
09f0bfb5-6283-4a95-9869-c1e37e2d1e46	emailVerified	user.attribute
09f0bfb5-6283-4a95-9869-c1e37e2d1e46	true	id.token.claim
09f0bfb5-6283-4a95-9869-c1e37e2d1e46	true	access.token.claim
09f0bfb5-6283-4a95-9869-c1e37e2d1e46	email_verified	claim.name
09f0bfb5-6283-4a95-9869-c1e37e2d1e46	boolean	jsonType.label
3d1204f4-1dfd-4242-8bad-81ab60eab378	true	introspection.token.claim
3d1204f4-1dfd-4242-8bad-81ab60eab378	true	userinfo.token.claim
3d1204f4-1dfd-4242-8bad-81ab60eab378	email	user.attribute
3d1204f4-1dfd-4242-8bad-81ab60eab378	true	id.token.claim
3d1204f4-1dfd-4242-8bad-81ab60eab378	true	access.token.claim
3d1204f4-1dfd-4242-8bad-81ab60eab378	email	claim.name
3d1204f4-1dfd-4242-8bad-81ab60eab378	String	jsonType.label
30207414-3f33-4d22-b4d1-2a274035cd0e	true	introspection.token.claim
30207414-3f33-4d22-b4d1-2a274035cd0e	true	userinfo.token.claim
30207414-3f33-4d22-b4d1-2a274035cd0e	phoneNumberVerified	user.attribute
30207414-3f33-4d22-b4d1-2a274035cd0e	true	id.token.claim
30207414-3f33-4d22-b4d1-2a274035cd0e	true	access.token.claim
30207414-3f33-4d22-b4d1-2a274035cd0e	phone_number_verified	claim.name
30207414-3f33-4d22-b4d1-2a274035cd0e	boolean	jsonType.label
eaebfe93-b362-48a6-ad98-9482105e198a	true	introspection.token.claim
eaebfe93-b362-48a6-ad98-9482105e198a	true	userinfo.token.claim
eaebfe93-b362-48a6-ad98-9482105e198a	phoneNumber	user.attribute
eaebfe93-b362-48a6-ad98-9482105e198a	true	id.token.claim
eaebfe93-b362-48a6-ad98-9482105e198a	true	access.token.claim
eaebfe93-b362-48a6-ad98-9482105e198a	phone_number	claim.name
eaebfe93-b362-48a6-ad98-9482105e198a	String	jsonType.label
f9763113-f5b5-47be-b4db-424d2874a42a	true	introspection.token.claim
f9763113-f5b5-47be-b4db-424d2874a42a	true	userinfo.token.claim
f9763113-f5b5-47be-b4db-424d2874a42a	locale	user.attribute
f9763113-f5b5-47be-b4db-424d2874a42a	true	id.token.claim
f9763113-f5b5-47be-b4db-424d2874a42a	true	access.token.claim
f9763113-f5b5-47be-b4db-424d2874a42a	locale	claim.name
f9763113-f5b5-47be-b4db-424d2874a42a	String	jsonType.label
045a7689-c250-4126-ae09-3a5db53a6a60	AUTH_TIME	user.session.note
045a7689-c250-4126-ae09-3a5db53a6a60	true	introspection.token.claim
045a7689-c250-4126-ae09-3a5db53a6a60	true	id.token.claim
045a7689-c250-4126-ae09-3a5db53a6a60	true	access.token.claim
045a7689-c250-4126-ae09-3a5db53a6a60	auth_time	claim.name
045a7689-c250-4126-ae09-3a5db53a6a60	long	jsonType.label
0c965fb9-4b04-4d44-95d8-f76f3068c434	true	introspection.token.claim
0c965fb9-4b04-4d44-95d8-f76f3068c434	true	access.token.claim
187d850f-068b-441f-bce3-69bb4d44bd3a	clientAddress	user.session.note
187d850f-068b-441f-bce3-69bb4d44bd3a	true	introspection.token.claim
187d850f-068b-441f-bce3-69bb4d44bd3a	true	userinfo.token.claim
187d850f-068b-441f-bce3-69bb4d44bd3a	true	id.token.claim
187d850f-068b-441f-bce3-69bb4d44bd3a	true	access.token.claim
187d850f-068b-441f-bce3-69bb4d44bd3a	clientAddress	claim.name
187d850f-068b-441f-bce3-69bb4d44bd3a	String	jsonType.label
adaf7b29-d596-437c-80ae-cb090c1f8f24	clientHost	user.session.note
adaf7b29-d596-437c-80ae-cb090c1f8f24	true	introspection.token.claim
adaf7b29-d596-437c-80ae-cb090c1f8f24	true	userinfo.token.claim
adaf7b29-d596-437c-80ae-cb090c1f8f24	true	id.token.claim
adaf7b29-d596-437c-80ae-cb090c1f8f24	true	access.token.claim
adaf7b29-d596-437c-80ae-cb090c1f8f24	clientHost	claim.name
adaf7b29-d596-437c-80ae-cb090c1f8f24	String	jsonType.label
c461e608-ddfa-4948-a145-fb2337438a92	client_id	user.session.note
c461e608-ddfa-4948-a145-fb2337438a92	true	introspection.token.claim
c461e608-ddfa-4948-a145-fb2337438a92	true	userinfo.token.claim
c461e608-ddfa-4948-a145-fb2337438a92	true	id.token.claim
c461e608-ddfa-4948-a145-fb2337438a92	true	access.token.claim
c461e608-ddfa-4948-a145-fb2337438a92	client_id	claim.name
c461e608-ddfa-4948-a145-fb2337438a92	String	jsonType.label
e6983fb8-cece-4662-90b4-77ed33eddcd3	true	introspection.token.claim
e6983fb8-cece-4662-90b4-77ed33eddcd3	true	multivalued
e6983fb8-cece-4662-90b4-77ed33eddcd3	true	userinfo.token.claim
e6983fb8-cece-4662-90b4-77ed33eddcd3	foo	user.attribute
e6983fb8-cece-4662-90b4-77ed33eddcd3	true	id.token.claim
e6983fb8-cece-4662-90b4-77ed33eddcd3	true	lightweight.claim
e6983fb8-cece-4662-90b4-77ed33eddcd3	true	access.token.claim
e6983fb8-cece-4662-90b4-77ed33eddcd3	realm_access.roles	claim.name
e6983fb8-cece-4662-90b4-77ed33eddcd3	String	jsonType.label
\.


--
-- Data for Name: realm; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm (id, access_code_lifespan, user_action_lifespan, access_token_lifespan, account_theme, admin_theme, email_theme, enabled, events_enabled, events_expiration, login_theme, name, not_before, password_policy, registration_allowed, remember_me, reset_password_allowed, social, ssl_required, sso_idle_timeout, sso_max_lifespan, update_profile_on_soc_login, verify_email, master_admin_client, login_lifespan, internationalization_enabled, default_locale, reg_email_as_username, admin_events_enabled, admin_events_details_enabled, edit_username_allowed, otp_policy_counter, otp_policy_window, otp_policy_period, otp_policy_digits, otp_policy_alg, otp_policy_type, browser_flow, registration_flow, direct_grant_flow, reset_credentials_flow, client_auth_flow, offline_session_idle_timeout, revoke_refresh_token, access_token_life_implicit, login_with_email_allowed, duplicate_emails_allowed, docker_auth_flow, refresh_token_max_reuse, allow_user_managed_access, sso_max_lifespan_remember_me, sso_idle_timeout_remember_me, default_role) FROM stdin;
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	60	300	60	\N	\N	\N	t	f	0	\N	master	0	\N	f	f	f	f	EXTERNAL	1800	36000	f	f	52a54993-a1a5-461a-bfe3-f27e68d50632	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	f58e92c0-92ac-454b-b0f3-701dab19cedd	83df2d16-50d9-405a-9384-cd1b8c0d38e8	cd87196c-2dee-4942-9c0b-def480ad5ea1	80f361c4-a499-4715-b346-58540a3acf10	1ac85d62-1942-4c16-85ab-5e95ee5ca5db	2592000	f	900	t	f	42e13f5b-15ca-47e1-a34b-95c30cf2f1ba	0	f	0	0	b0474fc5-0f15-4f4f-b0be-55b37a4f64bc
866b2156-2043-4bd6-8375-6cff30af44a0	60	300	300	\N	\N	\N	t	f	0	\N	content-maker	0	\N	t	f	f	f	EXTERNAL	1800	36000	f	f	496a27a9-032c-4976-addd-ee39086ad486	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	edc14f70-88b4-4abe-8a7e-403ab91c992c	09fe1617-ce32-412e-94be-789780df440d	ecb82555-f2d3-4d58-b37c-1188bdad916d	00a2b84f-2720-4adf-a494-9b34222b35d7	5ea9ca02-51ee-4849-8ea4-87aba5726bf5	2592000	f	900	t	f	2dbd67e5-c1b4-4a13-a153-d9a80effe420	0	f	0	0	c2c84cc6-e077-4f7f-8328-1d36514b9d29
\.


--
-- Data for Name: realm_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_attribute (name, realm_id, value) FROM stdin;
_browser_header.contentSecurityPolicyReportOnly	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	
_browser_header.xContentTypeOptions	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	nosniff
_browser_header.referrerPolicy	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	no-referrer
_browser_header.xRobotsTag	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	none
_browser_header.xFrameOptions	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	SAMEORIGIN
_browser_header.contentSecurityPolicy	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	1; mode=block
_browser_header.strictTransportSecurity	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	max-age=31536000; includeSubDomains
bruteForceProtected	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	false
permanentLockout	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	false
maxTemporaryLockouts	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	0
maxFailureWaitSeconds	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	900
minimumQuickLoginWaitSeconds	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	60
waitIncrementSeconds	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	60
quickLoginCheckMilliSeconds	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	1000
maxDeltaTimeSeconds	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	43200
failureFactor	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	30
realmReusableOtpCode	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	false
firstBrokerLoginFlowId	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	5a41776e-5520-4360-be9f-f1d49291a035
displayName	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	Keycloak
displayNameHtml	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	<div class="kc-logo-text"><span>Keycloak</span></div>
defaultSignatureAlgorithm	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	RS256
offlineSessionMaxLifespanEnabled	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	false
offlineSessionMaxLifespan	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	5184000
_browser_header.contentSecurityPolicyReportOnly	866b2156-2043-4bd6-8375-6cff30af44a0	
_browser_header.xContentTypeOptions	866b2156-2043-4bd6-8375-6cff30af44a0	nosniff
_browser_header.referrerPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	no-referrer
_browser_header.xRobotsTag	866b2156-2043-4bd6-8375-6cff30af44a0	none
_browser_header.xFrameOptions	866b2156-2043-4bd6-8375-6cff30af44a0	SAMEORIGIN
_browser_header.contentSecurityPolicy	866b2156-2043-4bd6-8375-6cff30af44a0	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	866b2156-2043-4bd6-8375-6cff30af44a0	1; mode=block
_browser_header.strictTransportSecurity	866b2156-2043-4bd6-8375-6cff30af44a0	max-age=31536000; includeSubDomains
bruteForceProtected	866b2156-2043-4bd6-8375-6cff30af44a0	false
permanentLockout	866b2156-2043-4bd6-8375-6cff30af44a0	false
maxTemporaryLockouts	866b2156-2043-4bd6-8375-6cff30af44a0	0
maxFailureWaitSeconds	866b2156-2043-4bd6-8375-6cff30af44a0	900
minimumQuickLoginWaitSeconds	866b2156-2043-4bd6-8375-6cff30af44a0	60
waitIncrementSeconds	866b2156-2043-4bd6-8375-6cff30af44a0	60
quickLoginCheckMilliSeconds	866b2156-2043-4bd6-8375-6cff30af44a0	1000
maxDeltaTimeSeconds	866b2156-2043-4bd6-8375-6cff30af44a0	43200
failureFactor	866b2156-2043-4bd6-8375-6cff30af44a0	30
realmReusableOtpCode	866b2156-2043-4bd6-8375-6cff30af44a0	false
displayName	866b2156-2043-4bd6-8375-6cff30af44a0	
displayNameHtml	866b2156-2043-4bd6-8375-6cff30af44a0	
defaultSignatureAlgorithm	866b2156-2043-4bd6-8375-6cff30af44a0	RS256
offlineSessionMaxLifespanEnabled	866b2156-2043-4bd6-8375-6cff30af44a0	false
offlineSessionMaxLifespan	866b2156-2043-4bd6-8375-6cff30af44a0	5184000
clientSessionIdleTimeout	866b2156-2043-4bd6-8375-6cff30af44a0	0
clientSessionMaxLifespan	866b2156-2043-4bd6-8375-6cff30af44a0	0
clientOfflineSessionIdleTimeout	866b2156-2043-4bd6-8375-6cff30af44a0	0
clientOfflineSessionMaxLifespan	866b2156-2043-4bd6-8375-6cff30af44a0	0
actionTokenGeneratedByAdminLifespan	866b2156-2043-4bd6-8375-6cff30af44a0	43200
actionTokenGeneratedByUserLifespan	866b2156-2043-4bd6-8375-6cff30af44a0	300
oauth2DeviceCodeLifespan	866b2156-2043-4bd6-8375-6cff30af44a0	600
oauth2DevicePollingInterval	866b2156-2043-4bd6-8375-6cff30af44a0	5
webAuthnPolicyRpEntityName	866b2156-2043-4bd6-8375-6cff30af44a0	keycloak
webAuthnPolicySignatureAlgorithms	866b2156-2043-4bd6-8375-6cff30af44a0	ES256
webAuthnPolicyRpId	866b2156-2043-4bd6-8375-6cff30af44a0	
webAuthnPolicyAttestationConveyancePreference	866b2156-2043-4bd6-8375-6cff30af44a0	not specified
webAuthnPolicyAuthenticatorAttachment	866b2156-2043-4bd6-8375-6cff30af44a0	not specified
webAuthnPolicyRequireResidentKey	866b2156-2043-4bd6-8375-6cff30af44a0	not specified
webAuthnPolicyUserVerificationRequirement	866b2156-2043-4bd6-8375-6cff30af44a0	not specified
webAuthnPolicyCreateTimeout	866b2156-2043-4bd6-8375-6cff30af44a0	0
webAuthnPolicyAvoidSameAuthenticatorRegister	866b2156-2043-4bd6-8375-6cff30af44a0	false
webAuthnPolicyRpEntityNamePasswordless	866b2156-2043-4bd6-8375-6cff30af44a0	keycloak
webAuthnPolicySignatureAlgorithmsPasswordless	866b2156-2043-4bd6-8375-6cff30af44a0	ES256
webAuthnPolicyRpIdPasswordless	866b2156-2043-4bd6-8375-6cff30af44a0	
webAuthnPolicyAttestationConveyancePreferencePasswordless	866b2156-2043-4bd6-8375-6cff30af44a0	not specified
webAuthnPolicyAuthenticatorAttachmentPasswordless	866b2156-2043-4bd6-8375-6cff30af44a0	not specified
webAuthnPolicyRequireResidentKeyPasswordless	866b2156-2043-4bd6-8375-6cff30af44a0	not specified
webAuthnPolicyUserVerificationRequirementPasswordless	866b2156-2043-4bd6-8375-6cff30af44a0	not specified
webAuthnPolicyCreateTimeoutPasswordless	866b2156-2043-4bd6-8375-6cff30af44a0	0
webAuthnPolicyAvoidSameAuthenticatorRegisterPasswordless	866b2156-2043-4bd6-8375-6cff30af44a0	false
cibaBackchannelTokenDeliveryMode	866b2156-2043-4bd6-8375-6cff30af44a0	poll
cibaExpiresIn	866b2156-2043-4bd6-8375-6cff30af44a0	120
cibaInterval	866b2156-2043-4bd6-8375-6cff30af44a0	5
cibaAuthRequestedUserHint	866b2156-2043-4bd6-8375-6cff30af44a0	login_hint
parRequestUriLifespan	866b2156-2043-4bd6-8375-6cff30af44a0	60
firstBrokerLoginFlowId	866b2156-2043-4bd6-8375-6cff30af44a0	04761fa8-7184-4082-80cc-de40295f3aaf
frontendUrl	866b2156-2043-4bd6-8375-6cff30af44a0	
acr.loa.map	866b2156-2043-4bd6-8375-6cff30af44a0	{}
client-policies.profiles	866b2156-2043-4bd6-8375-6cff30af44a0	{"profiles":[]}
client-policies.policies	866b2156-2043-4bd6-8375-6cff30af44a0	{"policies":[]}
\.


--
-- Data for Name: realm_default_groups; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_default_groups (realm_id, group_id) FROM stdin;
\.


--
-- Data for Name: realm_enabled_event_types; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_enabled_event_types (realm_id, value) FROM stdin;
\.


--
-- Data for Name: realm_events_listeners; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_events_listeners (realm_id, value) FROM stdin;
54bbc134-2bbd-41a2-bd63-2f1b765cd38a	jboss-logging
866b2156-2043-4bd6-8375-6cff30af44a0	jboss-logging
\.


--
-- Data for Name: realm_localizations; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_localizations (realm_id, locale, texts) FROM stdin;
\.


--
-- Data for Name: realm_required_credential; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_required_credential (type, form_label, input, secret, realm_id) FROM stdin;
password	password	t	t	54bbc134-2bbd-41a2-bd63-2f1b765cd38a
password	password	t	t	866b2156-2043-4bd6-8375-6cff30af44a0
\.


--
-- Data for Name: realm_smtp_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_smtp_config (realm_id, value, name) FROM stdin;
\.


--
-- Data for Name: realm_supported_locales; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_supported_locales (realm_id, value) FROM stdin;
\.


--
-- Data for Name: redirect_uris; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.redirect_uris (client_id, value) FROM stdin;
b13f0d0f-58f7-4834-b608-a1ffb579c343	/realms/master/account/*
6be14e92-ab08-4735-a7c3-9077efc21122	/realms/master/account/*
42284954-6f22-48fe-b901-e61519350f85	/admin/master/console/*
b46397d7-5fed-4d25-be0d-2957ef5534ec	/realms/content-maker/account/*
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	/admin/content-maker/console/*
22cd5734-9234-4009-8349-809a8168d548	http://localhost:8080/*
a8d9a0dc-08c9-486f-bbb3-8522b16cb578	/realms/content-maker/account/*
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	http://localhost:3000/*
\.


--
-- Data for Name: required_action_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.required_action_config (required_action_id, value, name) FROM stdin;
\.


--
-- Data for Name: required_action_provider; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.required_action_provider (id, alias, name, realm_id, enabled, default_action, provider_id, priority) FROM stdin;
2ae15f61-6028-4e21-8f3f-9cab8fc4ed0c	VERIFY_EMAIL	Verify Email	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	t	f	VERIFY_EMAIL	50
e21cc548-2fee-40e6-b98c-fddc571e1854	UPDATE_PROFILE	Update Profile	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	t	f	UPDATE_PROFILE	40
6bf41d6f-9915-4aff-8099-b1328c992248	CONFIGURE_TOTP	Configure OTP	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	t	f	CONFIGURE_TOTP	10
84d06763-5a59-48e2-8e74-e5967a9c98f2	UPDATE_PASSWORD	Update Password	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	t	f	UPDATE_PASSWORD	30
40c7d68b-daf0-4a53-b23f-0e8f75ca0c4e	TERMS_AND_CONDITIONS	Terms and Conditions	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f	f	TERMS_AND_CONDITIONS	20
dd8aad91-aab2-410a-9206-5817a8d4f6f5	delete_account	Delete Account	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	f	f	delete_account	60
de5f0631-f391-4c76-923b-07b02db484a1	delete_credential	Delete Credential	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	t	f	delete_credential	100
9c816b08-961f-4b8f-831b-0975addbc600	update_user_locale	Update User Locale	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	t	f	update_user_locale	1000
e0fd1491-8df4-45a1-9fde-47af92dc89ee	webauthn-register	Webauthn Register	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	t	f	webauthn-register	70
6abe6be9-7aaa-4f0d-ac72-da619594bac6	webauthn-register-passwordless	Webauthn Register Passwordless	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	t	f	webauthn-register-passwordless	80
46952896-dc48-40bc-bd40-44ee8ab66a90	VERIFY_PROFILE	Verify Profile	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	t	f	VERIFY_PROFILE	90
26c01980-ab1c-49b1-a484-c955cb36f021	CONFIGURE_TOTP	Configure OTP	866b2156-2043-4bd6-8375-6cff30af44a0	t	f	CONFIGURE_TOTP	10
61486250-a498-4959-9e9b-5ca5226296cc	TERMS_AND_CONDITIONS	Terms and Conditions	866b2156-2043-4bd6-8375-6cff30af44a0	f	f	TERMS_AND_CONDITIONS	20
4f1e12db-8ffe-4718-9d5b-013519556db0	UPDATE_PASSWORD	Update Password	866b2156-2043-4bd6-8375-6cff30af44a0	t	f	UPDATE_PASSWORD	30
27e16410-d04f-45ef-bd95-54aa5902c48f	UPDATE_PROFILE	Update Profile	866b2156-2043-4bd6-8375-6cff30af44a0	t	f	UPDATE_PROFILE	40
5dea2d6f-7396-4db0-94f7-c349dd30f3e0	VERIFY_EMAIL	Verify Email	866b2156-2043-4bd6-8375-6cff30af44a0	t	f	VERIFY_EMAIL	50
fed3e750-f98a-4b3d-999c-db81aa851bc4	delete_account	Delete Account	866b2156-2043-4bd6-8375-6cff30af44a0	f	f	delete_account	60
b4229639-090c-4aec-8beb-10387d32fc1f	webauthn-register	Webauthn Register	866b2156-2043-4bd6-8375-6cff30af44a0	t	f	webauthn-register	70
472410c7-f996-4a6b-ae17-6260ca94a1b2	webauthn-register-passwordless	Webauthn Register Passwordless	866b2156-2043-4bd6-8375-6cff30af44a0	t	f	webauthn-register-passwordless	80
348784db-dfdd-4d37-8c57-c877bb0d9fc1	VERIFY_PROFILE	Verify Profile	866b2156-2043-4bd6-8375-6cff30af44a0	t	f	VERIFY_PROFILE	90
c315c385-8f54-4adc-9228-5d7af90f0ad4	delete_credential	Delete Credential	866b2156-2043-4bd6-8375-6cff30af44a0	t	f	delete_credential	100
5f916f0c-9161-4f0b-beff-5fc0dc465d76	update_user_locale	Update User Locale	866b2156-2043-4bd6-8375-6cff30af44a0	t	f	update_user_locale	1000
\.


--
-- Data for Name: resource_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_attribute (id, name, value, resource_id) FROM stdin;
\.


--
-- Data for Name: resource_policy; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_policy (resource_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_scope (resource_id, scope_id) FROM stdin;
\.


--
-- Data for Name: resource_server; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_server (id, allow_rs_remote_mgmt, policy_enforce_mode, decision_strategy) FROM stdin;
\.


--
-- Data for Name: resource_server_perm_ticket; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_server_perm_ticket (id, owner, requester, created_timestamp, granted_timestamp, resource_id, scope_id, resource_server_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_server_policy; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_server_policy (id, name, description, type, decision_strategy, logic, resource_server_id, owner) FROM stdin;
\.


--
-- Data for Name: resource_server_resource; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_server_resource (id, name, type, icon_uri, owner, resource_server_id, owner_managed_access, display_name) FROM stdin;
\.


--
-- Data for Name: resource_server_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_server_scope (id, name, icon_uri, resource_server_id, display_name) FROM stdin;
\.


--
-- Data for Name: resource_uris; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_uris (resource_id, value) FROM stdin;
\.


--
-- Data for Name: role_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.role_attribute (id, role_id, name, value) FROM stdin;
\.


--
-- Data for Name: scope_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.scope_mapping (client_id, role_id) FROM stdin;
6be14e92-ab08-4735-a7c3-9077efc21122	345adc5b-1e68-4c37-bd01-67ba4b871302
6be14e92-ab08-4735-a7c3-9077efc21122	1eb07f91-217b-4e84-9181-51fe8fb73641
\.


--
-- Data for Name: scope_policy; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.scope_policy (scope_id, policy_id) FROM stdin;
\.


--
-- Data for Name: stories; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.stories (id, bank_id, platform, font_size, preview_title, preview_title_color, preview_url, preview_gradient, approved) FROM stdin;
1	absolutbank	ALL PLATFORMS	\N	asdsasdasdasd	#000	/site/share/htdoc/_files/skins/mobws_story/absolutbank/ALL PLATFORMS/1_0.jpg	EMPTY	APPROVED
9	absolutbank	ALL PLATFORMS	\N	dasdasdasd	#000	/site/share/htdoc/_files/skins/mobws_story/absolutbank/ALL PLATFORMS/9_0.jpg	EMPTY	APPROVED
11	absolutbank	ALL PLATFORMS	\N	asdasdasasd	#000	/site/share/htdoc/_files/skins/mobws_story/absolutbank/ALL PLATFORMS/11_0.jpg	EMPTY	DELETED
10	absolutbank	ALL PLATFORMS	\N	asdasdasd	#000	/site/share/htdoc/_files/skins/mobws_story/absolutbank/ALL PLATFORMS/10_0.jpg	EMPTY	DELETED
\.


--
-- Data for Name: user_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_attribute (name, value, user_id, id, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
\.


--
-- Data for Name: user_consent; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_consent (id, client_id, user_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: user_consent_client_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_consent_client_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: user_entity; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_entity (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before) FROM stdin;
bc1d957b-fa19-4356-b7af-ee08cf6ec8b4	\N	670262b3-ed47-4f36-aaec-fec490fb32bc	f	t	\N	\N	\N	54bbc134-2bbd-41a2-bd63-2f1b765cd38a	admin	1722862062749	\N	0
be98932a-2c79-472b-b883-5d5eb4cef5e5	testmail@gmail.com	testmail@gmail.com	f	t	\N	admin	User	866b2156-2043-4bd6-8375-6cff30af44a0	user	1722862244546	\N	0
e79c7e47-0e29-45cb-b125-3791d34a356f	cccthafcdh@gmail.com	cccthafcdh@gmail.com	f	t	\N	asdasd	asdasd	866b2156-2043-4bd6-8375-6cff30af44a0	admin	1722862293091	\N	0
\.


--
-- Data for Name: user_federation_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_federation_config (user_federation_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_federation_mapper (id, name, federation_provider_id, federation_mapper_type, realm_id) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_federation_mapper_config (user_federation_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_provider; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_federation_provider (id, changed_sync_period, display_name, full_sync_period, last_sync, priority, provider_name, realm_id) FROM stdin;
\.


--
-- Data for Name: user_group_membership; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_group_membership (group_id, user_id) FROM stdin;
\.


--
-- Data for Name: user_required_action; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_required_action (user_id, required_action) FROM stdin;
\.


--
-- Data for Name: user_role_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_role_mapping (role_id, user_id) FROM stdin;
b0474fc5-0f15-4f4f-b0be-55b37a4f64bc	bc1d957b-fa19-4356-b7af-ee08cf6ec8b4
119defff-e507-42b8-8a38-ff9a18f61179	bc1d957b-fa19-4356-b7af-ee08cf6ec8b4
c2c84cc6-e077-4f7f-8328-1d36514b9d29	be98932a-2c79-472b-b883-5d5eb4cef5e5
2b00c7a1-0961-4b38-ac9a-3be794211c12	be98932a-2c79-472b-b883-5d5eb4cef5e5
c2c84cc6-e077-4f7f-8328-1d36514b9d29	e79c7e47-0e29-45cb-b125-3791d34a356f
47040490-7cd5-4bb4-96dd-8e284781cff0	e79c7e47-0e29-45cb-b125-3791d34a356f
\.


--
-- Data for Name: user_session; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_session (id, auth_method, ip_address, last_session_refresh, login_username, realm_id, remember_me, started, user_id, user_session_state, broker_session_id, broker_user_id) FROM stdin;
\.


--
-- Data for Name: user_session_note; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_session_note (user_session, name, value) FROM stdin;
\.


--
-- Data for Name: username_login_failure; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.username_login_failure (realm_id, username, failed_login_not_before, last_failure, last_ip_failure, num_failures) FROM stdin;
\.


--
-- Data for Name: web_origins; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.web_origins (client_id, value) FROM stdin;
42284954-6f22-48fe-b901-e61519350f85	+
f59be1f0-e7c9-47c1-8a18-7d06f89b1ef9	+
22cd5734-9234-4009-8349-809a8168d548	http://localhost:8080
949ba0b8-9ec0-49c2-b5a3-efe1e3e139aa	http://localhost:3000
\.


--
-- Name: history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.history_id_seq', 73, true);


--
-- Name: stories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.stories_id_seq', 11, true);


--
-- Name: username_login_failure CONSTRAINT_17-2; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.username_login_failure
    ADD CONSTRAINT "CONSTRAINT_17-2" PRIMARY KEY (realm_id, username);


--
-- Name: org_domain ORG_DOMAIN_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.org_domain
    ADD CONSTRAINT "ORG_DOMAIN_pkey" PRIMARY KEY (id, name);


--
-- Name: org ORG_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT "ORG_pkey" PRIMARY KEY (id);


--
-- Name: keycloak_role UK_J3RWUVD56ONTGSUHOGM184WW2-2; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT "UK_J3RWUVD56ONTGSUHOGM184WW2-2" UNIQUE (name, client_realm_constraint);


--
-- Name: client_auth_flow_bindings c_cli_flow_bind; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_auth_flow_bindings
    ADD CONSTRAINT c_cli_flow_bind PRIMARY KEY (client_id, binding_name);


--
-- Name: client_scope_client c_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope_client
    ADD CONSTRAINT c_cli_scope_bind PRIMARY KEY (client_id, scope_id);


--
-- Name: client_initial_access cnstr_client_init_acc_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT cnstr_client_init_acc_pk PRIMARY KEY (id);


--
-- Name: realm_default_groups con_group_id_def_groups; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT con_group_id_def_groups UNIQUE (group_id);


--
-- Name: broker_link constr_broker_link_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.broker_link
    ADD CONSTRAINT constr_broker_link_pk PRIMARY KEY (identity_provider, user_id);


--
-- Name: client_user_session_note constr_cl_usr_ses_note; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_user_session_note
    ADD CONSTRAINT constr_cl_usr_ses_note PRIMARY KEY (client_session, name);


--
-- Name: component_config constr_component_config_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT constr_component_config_pk PRIMARY KEY (id);


--
-- Name: component constr_component_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT constr_component_pk PRIMARY KEY (id);


--
-- Name: fed_user_required_action constr_fed_required_action; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_required_action
    ADD CONSTRAINT constr_fed_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: fed_user_attribute constr_fed_user_attr_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_attribute
    ADD CONSTRAINT constr_fed_user_attr_pk PRIMARY KEY (id);


--
-- Name: fed_user_consent constr_fed_user_consent_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_consent
    ADD CONSTRAINT constr_fed_user_consent_pk PRIMARY KEY (id);


--
-- Name: fed_user_credential constr_fed_user_cred_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_credential
    ADD CONSTRAINT constr_fed_user_cred_pk PRIMARY KEY (id);


--
-- Name: fed_user_group_membership constr_fed_user_group; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_group_membership
    ADD CONSTRAINT constr_fed_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: fed_user_role_mapping constr_fed_user_role; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_role_mapping
    ADD CONSTRAINT constr_fed_user_role PRIMARY KEY (role_id, user_id);


--
-- Name: federated_user constr_federated_user; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.federated_user
    ADD CONSTRAINT constr_federated_user PRIMARY KEY (id);


--
-- Name: realm_default_groups constr_realm_default_groups; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT constr_realm_default_groups PRIMARY KEY (realm_id, group_id);


--
-- Name: realm_enabled_event_types constr_realm_enabl_event_types; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT constr_realm_enabl_event_types PRIMARY KEY (realm_id, value);


--
-- Name: realm_events_listeners constr_realm_events_listeners; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT constr_realm_events_listeners PRIMARY KEY (realm_id, value);


--
-- Name: realm_supported_locales constr_realm_supported_locales; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT constr_realm_supported_locales PRIMARY KEY (realm_id, value);


--
-- Name: identity_provider constraint_2b; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT constraint_2b PRIMARY KEY (internal_id);


--
-- Name: client_attributes constraint_3c; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT constraint_3c PRIMARY KEY (client_id, name);


--
-- Name: event_entity constraint_4; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.event_entity
    ADD CONSTRAINT constraint_4 PRIMARY KEY (id);


--
-- Name: federated_identity constraint_40; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT constraint_40 PRIMARY KEY (identity_provider, user_id);


--
-- Name: realm constraint_4a; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT constraint_4a PRIMARY KEY (id);


--
-- Name: client_session_role constraint_5; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_session_role
    ADD CONSTRAINT constraint_5 PRIMARY KEY (client_session, role_id);


--
-- Name: user_session constraint_57; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_session
    ADD CONSTRAINT constraint_57 PRIMARY KEY (id);


--
-- Name: user_federation_provider constraint_5c; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT constraint_5c PRIMARY KEY (id);


--
-- Name: client_session_note constraint_5e; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_session_note
    ADD CONSTRAINT constraint_5e PRIMARY KEY (client_session, name);


--
-- Name: client constraint_7; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT constraint_7 PRIMARY KEY (id);


--
-- Name: client_session constraint_8; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_session
    ADD CONSTRAINT constraint_8 PRIMARY KEY (id);


--
-- Name: scope_mapping constraint_81; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT constraint_81 PRIMARY KEY (client_id, role_id);


--
-- Name: client_node_registrations constraint_84; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT constraint_84 PRIMARY KEY (client_id, name);


--
-- Name: realm_attribute constraint_9; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT constraint_9 PRIMARY KEY (name, realm_id);


--
-- Name: realm_required_credential constraint_92; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT constraint_92 PRIMARY KEY (realm_id, type);


--
-- Name: keycloak_role constraint_a; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT constraint_a PRIMARY KEY (id);


--
-- Name: admin_event_entity constraint_admin_event_entity; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.admin_event_entity
    ADD CONSTRAINT constraint_admin_event_entity PRIMARY KEY (id);


--
-- Name: authenticator_config_entry constraint_auth_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authenticator_config_entry
    ADD CONSTRAINT constraint_auth_cfg_pk PRIMARY KEY (authenticator_id, name);


--
-- Name: authentication_execution constraint_auth_exec_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT constraint_auth_exec_pk PRIMARY KEY (id);


--
-- Name: authentication_flow constraint_auth_flow_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT constraint_auth_flow_pk PRIMARY KEY (id);


--
-- Name: authenticator_config constraint_auth_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT constraint_auth_pk PRIMARY KEY (id);


--
-- Name: client_session_auth_status constraint_auth_status_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_session_auth_status
    ADD CONSTRAINT constraint_auth_status_pk PRIMARY KEY (client_session, authenticator);


--
-- Name: user_role_mapping constraint_c; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT constraint_c PRIMARY KEY (role_id, user_id);


--
-- Name: composite_role constraint_composite_role; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT constraint_composite_role PRIMARY KEY (composite, child_role);


--
-- Name: client_session_prot_mapper constraint_cs_pmp_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_session_prot_mapper
    ADD CONSTRAINT constraint_cs_pmp_pk PRIMARY KEY (client_session, protocol_mapper_id);


--
-- Name: identity_provider_config constraint_d; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT constraint_d PRIMARY KEY (identity_provider_id, name);


--
-- Name: policy_config constraint_dpc; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT constraint_dpc PRIMARY KEY (policy_id, name);


--
-- Name: realm_smtp_config constraint_e; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT constraint_e PRIMARY KEY (realm_id, name);


--
-- Name: credential constraint_f; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT constraint_f PRIMARY KEY (id);


--
-- Name: user_federation_config constraint_f9; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT constraint_f9 PRIMARY KEY (user_federation_provider_id, name);


--
-- Name: resource_server_perm_ticket constraint_fapmt; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT constraint_fapmt PRIMARY KEY (id);


--
-- Name: resource_server_resource constraint_farsr; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT constraint_farsr PRIMARY KEY (id);


--
-- Name: resource_server_policy constraint_farsrp; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT constraint_farsrp PRIMARY KEY (id);


--
-- Name: associated_policy constraint_farsrpap; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT constraint_farsrpap PRIMARY KEY (policy_id, associated_policy_id);


--
-- Name: resource_policy constraint_farsrpp; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT constraint_farsrpp PRIMARY KEY (resource_id, policy_id);


--
-- Name: resource_server_scope constraint_farsrs; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT constraint_farsrs PRIMARY KEY (id);


--
-- Name: resource_scope constraint_farsrsp; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT constraint_farsrsp PRIMARY KEY (resource_id, scope_id);


--
-- Name: scope_policy constraint_farsrsps; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT constraint_farsrsps PRIMARY KEY (scope_id, policy_id);


--
-- Name: user_entity constraint_fb; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT constraint_fb PRIMARY KEY (id);


--
-- Name: user_federation_mapper_config constraint_fedmapper_cfg_pm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT constraint_fedmapper_cfg_pm PRIMARY KEY (user_federation_mapper_id, name);


--
-- Name: user_federation_mapper constraint_fedmapperpm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT constraint_fedmapperpm PRIMARY KEY (id);


--
-- Name: fed_user_consent_cl_scope constraint_fgrntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_consent_cl_scope
    ADD CONSTRAINT constraint_fgrntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent_client_scope constraint_grntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT constraint_grntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent constraint_grntcsnt_pm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT constraint_grntcsnt_pm PRIMARY KEY (id);


--
-- Name: keycloak_group constraint_group; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT constraint_group PRIMARY KEY (id);


--
-- Name: group_attribute constraint_group_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT constraint_group_attribute_pk PRIMARY KEY (id);


--
-- Name: group_role_mapping constraint_group_role; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT constraint_group_role PRIMARY KEY (role_id, group_id);


--
-- Name: identity_provider_mapper constraint_idpm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT constraint_idpm PRIMARY KEY (id);


--
-- Name: idp_mapper_config constraint_idpmconfig; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT constraint_idpmconfig PRIMARY KEY (idp_mapper_id, name);


--
-- Name: migration_model constraint_migmod; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT constraint_migmod PRIMARY KEY (id);


--
-- Name: offline_client_session constraint_offl_cl_ses_pk3; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.offline_client_session
    ADD CONSTRAINT constraint_offl_cl_ses_pk3 PRIMARY KEY (user_session_id, client_id, client_storage_provider, external_client_id, offline_flag);


--
-- Name: offline_user_session constraint_offl_us_ses_pk2; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.offline_user_session
    ADD CONSTRAINT constraint_offl_us_ses_pk2 PRIMARY KEY (user_session_id, offline_flag);


--
-- Name: protocol_mapper constraint_pcm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT constraint_pcm PRIMARY KEY (id);


--
-- Name: protocol_mapper_config constraint_pmconfig; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT constraint_pmconfig PRIMARY KEY (protocol_mapper_id, name);


--
-- Name: redirect_uris constraint_redirect_uris; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT constraint_redirect_uris PRIMARY KEY (client_id, value);


--
-- Name: required_action_config constraint_req_act_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.required_action_config
    ADD CONSTRAINT constraint_req_act_cfg_pk PRIMARY KEY (required_action_id, name);


--
-- Name: required_action_provider constraint_req_act_prv_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT constraint_req_act_prv_pk PRIMARY KEY (id);


--
-- Name: user_required_action constraint_required_action; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT constraint_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: resource_uris constraint_resour_uris_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT constraint_resour_uris_pk PRIMARY KEY (resource_id, value);


--
-- Name: role_attribute constraint_role_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT constraint_role_attribute_pk PRIMARY KEY (id);


--
-- Name: user_attribute constraint_user_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT constraint_user_attribute_pk PRIMARY KEY (id);


--
-- Name: user_group_membership constraint_user_group; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT constraint_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: user_session_note constraint_usn_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_session_note
    ADD CONSTRAINT constraint_usn_pk PRIMARY KEY (user_session, name);


--
-- Name: web_origins constraint_web_origins; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT constraint_web_origins PRIMARY KEY (client_id, value);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: frames frames_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.frames
    ADD CONSTRAINT frames_pkey PRIMARY KEY (id);


--
-- Name: history history_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_pkey PRIMARY KEY (id);


--
-- Name: client_scope_attributes pk_cl_tmpl_attr; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT pk_cl_tmpl_attr PRIMARY KEY (scope_id, name);


--
-- Name: client_scope pk_cli_template; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT pk_cli_template PRIMARY KEY (id);


--
-- Name: resource_server pk_resource_server; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server
    ADD CONSTRAINT pk_resource_server PRIMARY KEY (id);


--
-- Name: client_scope_role_mapping pk_template_scope; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT pk_template_scope PRIMARY KEY (scope_id, role_id);


--
-- Name: default_client_scope r_def_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT r_def_cli_scope_bind PRIMARY KEY (realm_id, scope_id);


--
-- Name: realm_localizations realm_localizations_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_localizations
    ADD CONSTRAINT realm_localizations_pkey PRIMARY KEY (realm_id, locale);


--
-- Name: resource_attribute res_attr_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT res_attr_pk PRIMARY KEY (id);


--
-- Name: keycloak_group sibling_names; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT sibling_names UNIQUE (realm_id, parent_group, name);


--
-- Name: stories stories_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.stories
    ADD CONSTRAINT stories_pkey PRIMARY KEY (id);


--
-- Name: identity_provider uk_2daelwnibji49avxsrtuf6xj33; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT uk_2daelwnibji49avxsrtuf6xj33 UNIQUE (provider_alias, realm_id);


--
-- Name: client uk_b71cjlbenv945rb6gcon438at; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk_b71cjlbenv945rb6gcon438at UNIQUE (realm_id, client_id);


--
-- Name: client_scope uk_cli_scope; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT uk_cli_scope UNIQUE (realm_id, name);


--
-- Name: user_entity uk_dykn684sl8up1crfei6eckhd7; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_dykn684sl8up1crfei6eckhd7 UNIQUE (realm_id, email_constraint);


--
-- Name: user_consent uk_external_consent; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_external_consent UNIQUE (client_storage_provider, external_client_id, user_id);


--
-- Name: resource_server_resource uk_frsr6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5ha6 UNIQUE (name, owner, resource_server_id);


--
-- Name: resource_server_perm_ticket uk_frsr6t700s9v50bu18ws5pmt; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5pmt UNIQUE (owner, requester, resource_server_id, resource_id, scope_id);


--
-- Name: resource_server_policy uk_frsrpt700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT uk_frsrpt700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: resource_server_scope uk_frsrst700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT uk_frsrst700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: user_consent uk_local_consent; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_local_consent UNIQUE (client_id, user_id);


--
-- Name: org uk_org_group; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_group UNIQUE (group_id);


--
-- Name: org uk_org_name; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_name UNIQUE (realm_id, name);


--
-- Name: realm uk_orvsdmla56612eaefiq6wl5oi; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT uk_orvsdmla56612eaefiq6wl5oi UNIQUE (name);


--
-- Name: user_entity uk_ru8tt6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_ru8tt6t700s9v50bu18ws5ha6 UNIQUE (realm_id, username);


--
-- Name: fed_user_attr_long_values; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX fed_user_attr_long_values ON public.fed_user_attribute USING btree (long_value_hash, name);


--
-- Name: fed_user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX fed_user_attr_long_values_lower_case ON public.fed_user_attribute USING btree (long_value_hash_lower_case, name);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: idx_admin_event_time; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_admin_event_time ON public.admin_event_entity USING btree (realm_id, admin_event_time);


--
-- Name: idx_assoc_pol_assoc_pol_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_assoc_pol_assoc_pol_id ON public.associated_policy USING btree (associated_policy_id);


--
-- Name: idx_auth_config_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_auth_config_realm ON public.authenticator_config USING btree (realm_id);


--
-- Name: idx_auth_exec_flow; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_auth_exec_flow ON public.authentication_execution USING btree (flow_id);


--
-- Name: idx_auth_exec_realm_flow; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_auth_exec_realm_flow ON public.authentication_execution USING btree (realm_id, flow_id);


--
-- Name: idx_auth_flow_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_auth_flow_realm ON public.authentication_flow USING btree (realm_id);


--
-- Name: idx_cl_clscope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_cl_clscope ON public.client_scope_client USING btree (scope_id);


--
-- Name: idx_client_att_by_name_value; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_client_att_by_name_value ON public.client_attributes USING btree (name, substr(value, 1, 255));


--
-- Name: idx_client_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_client_id ON public.client USING btree (client_id);


--
-- Name: idx_client_init_acc_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_client_init_acc_realm ON public.client_initial_access USING btree (realm_id);


--
-- Name: idx_client_session_session; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_client_session_session ON public.client_session USING btree (session_id);


--
-- Name: idx_clscope_attrs; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_clscope_attrs ON public.client_scope_attributes USING btree (scope_id);


--
-- Name: idx_clscope_cl; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_clscope_cl ON public.client_scope_client USING btree (client_id);


--
-- Name: idx_clscope_protmap; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_clscope_protmap ON public.protocol_mapper USING btree (client_scope_id);


--
-- Name: idx_clscope_role; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_clscope_role ON public.client_scope_role_mapping USING btree (scope_id);


--
-- Name: idx_compo_config_compo; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_compo_config_compo ON public.component_config USING btree (component_id);


--
-- Name: idx_component_provider_type; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_component_provider_type ON public.component USING btree (provider_type);


--
-- Name: idx_component_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_component_realm ON public.component USING btree (realm_id);


--
-- Name: idx_composite; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_composite ON public.composite_role USING btree (composite);


--
-- Name: idx_composite_child; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_composite_child ON public.composite_role USING btree (child_role);


--
-- Name: idx_defcls_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_defcls_realm ON public.default_client_scope USING btree (realm_id);


--
-- Name: idx_defcls_scope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_defcls_scope ON public.default_client_scope USING btree (scope_id);


--
-- Name: idx_event_time; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_event_time ON public.event_entity USING btree (realm_id, event_time);


--
-- Name: idx_fedidentity_feduser; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fedidentity_feduser ON public.federated_identity USING btree (federated_user_id);


--
-- Name: idx_fedidentity_user; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fedidentity_user ON public.federated_identity USING btree (user_id);


--
-- Name: idx_fu_attribute; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_attribute ON public.fed_user_attribute USING btree (user_id, realm_id, name);


--
-- Name: idx_fu_cnsnt_ext; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_cnsnt_ext ON public.fed_user_consent USING btree (user_id, client_storage_provider, external_client_id);


--
-- Name: idx_fu_consent; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_consent ON public.fed_user_consent USING btree (user_id, client_id);


--
-- Name: idx_fu_consent_ru; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_consent_ru ON public.fed_user_consent USING btree (realm_id, user_id);


--
-- Name: idx_fu_credential; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_credential ON public.fed_user_credential USING btree (user_id, type);


--
-- Name: idx_fu_credential_ru; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_credential_ru ON public.fed_user_credential USING btree (realm_id, user_id);


--
-- Name: idx_fu_group_membership; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_group_membership ON public.fed_user_group_membership USING btree (user_id, group_id);


--
-- Name: idx_fu_group_membership_ru; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_group_membership_ru ON public.fed_user_group_membership USING btree (realm_id, user_id);


--
-- Name: idx_fu_required_action; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_required_action ON public.fed_user_required_action USING btree (user_id, required_action);


--
-- Name: idx_fu_required_action_ru; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_required_action_ru ON public.fed_user_required_action USING btree (realm_id, user_id);


--
-- Name: idx_fu_role_mapping; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_role_mapping ON public.fed_user_role_mapping USING btree (user_id, role_id);


--
-- Name: idx_fu_role_mapping_ru; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_role_mapping_ru ON public.fed_user_role_mapping USING btree (realm_id, user_id);


--
-- Name: idx_group_att_by_name_value; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_group_att_by_name_value ON public.group_attribute USING btree (name, ((value)::character varying(250)));


--
-- Name: idx_group_attr_group; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_group_attr_group ON public.group_attribute USING btree (group_id);


--
-- Name: idx_group_role_mapp_group; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_group_role_mapp_group ON public.group_role_mapping USING btree (group_id);


--
-- Name: idx_id_prov_mapp_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_id_prov_mapp_realm ON public.identity_provider_mapper USING btree (realm_id);


--
-- Name: idx_ident_prov_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_ident_prov_realm ON public.identity_provider USING btree (realm_id);


--
-- Name: idx_keycloak_role_client; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_keycloak_role_client ON public.keycloak_role USING btree (client);


--
-- Name: idx_keycloak_role_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_keycloak_role_realm ON public.keycloak_role USING btree (realm);


--
-- Name: idx_offline_uss_by_broker_session_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_offline_uss_by_broker_session_id ON public.offline_user_session USING btree (broker_session_id, realm_id);


--
-- Name: idx_offline_uss_by_last_session_refresh; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_offline_uss_by_last_session_refresh ON public.offline_user_session USING btree (realm_id, offline_flag, last_session_refresh);


--
-- Name: idx_offline_uss_by_user; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_offline_uss_by_user ON public.offline_user_session USING btree (user_id, realm_id, offline_flag);


--
-- Name: idx_perm_ticket_owner; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_perm_ticket_owner ON public.resource_server_perm_ticket USING btree (owner);


--
-- Name: idx_perm_ticket_requester; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_perm_ticket_requester ON public.resource_server_perm_ticket USING btree (requester);


--
-- Name: idx_protocol_mapper_client; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_protocol_mapper_client ON public.protocol_mapper USING btree (client_id);


--
-- Name: idx_realm_attr_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_attr_realm ON public.realm_attribute USING btree (realm_id);


--
-- Name: idx_realm_clscope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_clscope ON public.client_scope USING btree (realm_id);


--
-- Name: idx_realm_def_grp_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_def_grp_realm ON public.realm_default_groups USING btree (realm_id);


--
-- Name: idx_realm_evt_list_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_evt_list_realm ON public.realm_events_listeners USING btree (realm_id);


--
-- Name: idx_realm_evt_types_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_evt_types_realm ON public.realm_enabled_event_types USING btree (realm_id);


--
-- Name: idx_realm_master_adm_cli; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_master_adm_cli ON public.realm USING btree (master_admin_client);


--
-- Name: idx_realm_supp_local_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_supp_local_realm ON public.realm_supported_locales USING btree (realm_id);


--
-- Name: idx_redir_uri_client; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_redir_uri_client ON public.redirect_uris USING btree (client_id);


--
-- Name: idx_req_act_prov_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_req_act_prov_realm ON public.required_action_provider USING btree (realm_id);


--
-- Name: idx_res_policy_policy; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_res_policy_policy ON public.resource_policy USING btree (policy_id);


--
-- Name: idx_res_scope_scope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_res_scope_scope ON public.resource_scope USING btree (scope_id);


--
-- Name: idx_res_serv_pol_res_serv; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_res_serv_pol_res_serv ON public.resource_server_policy USING btree (resource_server_id);


--
-- Name: idx_res_srv_res_res_srv; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_res_srv_res_res_srv ON public.resource_server_resource USING btree (resource_server_id);


--
-- Name: idx_res_srv_scope_res_srv; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_res_srv_scope_res_srv ON public.resource_server_scope USING btree (resource_server_id);


--
-- Name: idx_role_attribute; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_role_attribute ON public.role_attribute USING btree (role_id);


--
-- Name: idx_role_clscope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_role_clscope ON public.client_scope_role_mapping USING btree (role_id);


--
-- Name: idx_scope_mapping_role; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_scope_mapping_role ON public.scope_mapping USING btree (role_id);


--
-- Name: idx_scope_policy_policy; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_scope_policy_policy ON public.scope_policy USING btree (policy_id);


--
-- Name: idx_update_time; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_update_time ON public.migration_model USING btree (update_time);


--
-- Name: idx_us_sess_id_on_cl_sess; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_us_sess_id_on_cl_sess ON public.offline_client_session USING btree (user_session_id);


--
-- Name: idx_usconsent_clscope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_usconsent_clscope ON public.user_consent_client_scope USING btree (user_consent_id);


--
-- Name: idx_usconsent_scope_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_usconsent_scope_id ON public.user_consent_client_scope USING btree (scope_id);


--
-- Name: idx_user_attribute; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_attribute ON public.user_attribute USING btree (user_id);


--
-- Name: idx_user_attribute_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_attribute_name ON public.user_attribute USING btree (name, value);


--
-- Name: idx_user_consent; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_consent ON public.user_consent USING btree (user_id);


--
-- Name: idx_user_credential; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_credential ON public.credential USING btree (user_id);


--
-- Name: idx_user_email; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_email ON public.user_entity USING btree (email);


--
-- Name: idx_user_group_mapping; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_group_mapping ON public.user_group_membership USING btree (user_id);


--
-- Name: idx_user_reqactions; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_reqactions ON public.user_required_action USING btree (user_id);


--
-- Name: idx_user_role_mapping; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_role_mapping ON public.user_role_mapping USING btree (user_id);


--
-- Name: idx_user_service_account; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_service_account ON public.user_entity USING btree (realm_id, service_account_client_link);


--
-- Name: idx_usr_fed_map_fed_prv; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_usr_fed_map_fed_prv ON public.user_federation_mapper USING btree (federation_provider_id);


--
-- Name: idx_usr_fed_map_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_usr_fed_map_realm ON public.user_federation_mapper USING btree (realm_id);


--
-- Name: idx_usr_fed_prv_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_usr_fed_prv_realm ON public.user_federation_provider USING btree (realm_id);


--
-- Name: idx_web_orig_client; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_web_orig_client ON public.web_origins USING btree (client_id);


--
-- Name: user_attr_long_values; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX user_attr_long_values ON public.user_attribute USING btree (long_value_hash, name);


--
-- Name: user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX user_attr_long_values_lower_case ON public.user_attribute USING btree (long_value_hash_lower_case, name);


--
-- Name: client_session_auth_status auth_status_constraint; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_session_auth_status
    ADD CONSTRAINT auth_status_constraint FOREIGN KEY (client_session) REFERENCES public.client_session(id);


--
-- Name: identity_provider fk2b4ebc52ae5c3b34; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT fk2b4ebc52ae5c3b34 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: client_attributes fk3c47c64beacca966; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT fk3c47c64beacca966 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: federated_identity fk404288b92ef007a6; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT fk404288b92ef007a6 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_node_registrations fk4129723ba992f594; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT fk4129723ba992f594 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: client_session_note fk5edfb00ff51c2736; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_session_note
    ADD CONSTRAINT fk5edfb00ff51c2736 FOREIGN KEY (client_session) REFERENCES public.client_session(id);


--
-- Name: user_session_note fk5edfb00ff51d3472; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_session_note
    ADD CONSTRAINT fk5edfb00ff51d3472 FOREIGN KEY (user_session) REFERENCES public.user_session(id);


--
-- Name: client_session_role fk_11b7sgqw18i532811v7o2dv76; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_session_role
    ADD CONSTRAINT fk_11b7sgqw18i532811v7o2dv76 FOREIGN KEY (client_session) REFERENCES public.client_session(id);


--
-- Name: redirect_uris fk_1burs8pb4ouj97h5wuppahv9f; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT fk_1burs8pb4ouj97h5wuppahv9f FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: user_federation_provider fk_1fj32f6ptolw2qy60cd8n01e8; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT fk_1fj32f6ptolw2qy60cd8n01e8 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: client_session_prot_mapper fk_33a8sgqw18i532811v7o2dk89; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_session_prot_mapper
    ADD CONSTRAINT fk_33a8sgqw18i532811v7o2dk89 FOREIGN KEY (client_session) REFERENCES public.client_session(id);


--
-- Name: realm_required_credential fk_5hg65lybevavkqfki3kponh9v; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT fk_5hg65lybevavkqfki3kponh9v FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_attribute fk_5hrm2vlf9ql5fu022kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu022kqepovbr FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: user_attribute fk_5hrm2vlf9ql5fu043kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu043kqepovbr FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: user_required_action fk_6qj3w1jw9cvafhe19bwsiuvmd; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT fk_6qj3w1jw9cvafhe19bwsiuvmd FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: keycloak_role fk_6vyqfe4cn4wlq8r6kt5vdsj5c; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT fk_6vyqfe4cn4wlq8r6kt5vdsj5c FOREIGN KEY (realm) REFERENCES public.realm(id);


--
-- Name: realm_smtp_config fk_70ej8xdxgxd0b9hh6180irr0o; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT fk_70ej8xdxgxd0b9hh6180irr0o FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_attribute fk_8shxd6l3e9atqukacxgpffptw; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT fk_8shxd6l3e9atqukacxgpffptw FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: composite_role fk_a63wvekftu8jo1pnj81e7mce2; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_a63wvekftu8jo1pnj81e7mce2 FOREIGN KEY (composite) REFERENCES public.keycloak_role(id);


--
-- Name: authentication_execution fk_auth_exec_flow; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_flow FOREIGN KEY (flow_id) REFERENCES public.authentication_flow(id);


--
-- Name: authentication_execution fk_auth_exec_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authentication_flow fk_auth_flow_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT fk_auth_flow_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authenticator_config fk_auth_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT fk_auth_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: client_session fk_b4ao2vcvat6ukau74wbwtfqo1; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_session
    ADD CONSTRAINT fk_b4ao2vcvat6ukau74wbwtfqo1 FOREIGN KEY (session_id) REFERENCES public.user_session(id);


--
-- Name: user_role_mapping fk_c4fqv34p1mbylloxang7b1q3l; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT fk_c4fqv34p1mbylloxang7b1q3l FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_scope_attributes fk_cl_scope_attr_scope; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT fk_cl_scope_attr_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_scope_role_mapping fk_cl_scope_rm_scope; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT fk_cl_scope_rm_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_user_session_note fk_cl_usr_ses_note; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_user_session_note
    ADD CONSTRAINT fk_cl_usr_ses_note FOREIGN KEY (client_session) REFERENCES public.client_session(id);


--
-- Name: protocol_mapper fk_cli_scope_mapper; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_cli_scope_mapper FOREIGN KEY (client_scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_initial_access fk_client_init_acc_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT fk_client_init_acc_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: component_config fk_component_config; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT fk_component_config FOREIGN KEY (component_id) REFERENCES public.component(id);


--
-- Name: component fk_component_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT fk_component_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_default_groups fk_def_groups_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT fk_def_groups_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_mapper_config fk_fedmapper_cfg; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT fk_fedmapper_cfg FOREIGN KEY (user_federation_mapper_id) REFERENCES public.user_federation_mapper(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_fedprv; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_fedprv FOREIGN KEY (federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: associated_policy fk_frsr5s213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsr5s213xcx4wnkog82ssrfy FOREIGN KEY (associated_policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrasp13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrasp13xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog82sspmt; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82sspmt FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_resource fk_frsrho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog83sspmt; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog83sspmt FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog84sspmt; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog84sspmt FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: associated_policy fk_frsrpas14xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsrpas14xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrpass3xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrpass3xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_perm_ticket fk_frsrpo2128cx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrpo2128cx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_policy fk_frsrpo213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT fk_frsrpo213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_scope fk_frsrpos13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrpos13xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpos53xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpos53xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpp213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpp213xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_scope fk_frsrps213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrps213xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_scope fk_frsrso213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT fk_frsrso213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: composite_role fk_gr7thllb9lu8q4vqa4524jjy8; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_gr7thllb9lu8q4vqa4524jjy8 FOREIGN KEY (child_role) REFERENCES public.keycloak_role(id);


--
-- Name: user_consent_client_scope fk_grntcsnt_clsc_usc; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT fk_grntcsnt_clsc_usc FOREIGN KEY (user_consent_id) REFERENCES public.user_consent(id);


--
-- Name: user_consent fk_grntcsnt_user; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT fk_grntcsnt_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: group_attribute fk_group_attribute_group; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT fk_group_attribute_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: group_role_mapping fk_group_role_group; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT fk_group_role_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: realm_enabled_event_types fk_h846o4h0w8epx5nwedrf5y69j; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT fk_h846o4h0w8epx5nwedrf5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_events_listeners fk_h846o4h0w8epx5nxev9f5y69j; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT fk_h846o4h0w8epx5nxev9f5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: identity_provider_mapper fk_idpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT fk_idpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: idp_mapper_config fk_idpmconfig; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT fk_idpmconfig FOREIGN KEY (idp_mapper_id) REFERENCES public.identity_provider_mapper(id);


--
-- Name: web_origins fk_lojpho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT fk_lojpho213xcx4wnkog82ssrfy FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: scope_mapping fk_ouse064plmlr732lxjcn1q5f1; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT fk_ouse064plmlr732lxjcn1q5f1 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: protocol_mapper fk_pcm_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_pcm_realm FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: credential fk_pfyr0glasqyl0dei3kl69r6v0; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT fk_pfyr0glasqyl0dei3kl69r6v0 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: protocol_mapper_config fk_pmconfig; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT fk_pmconfig FOREIGN KEY (protocol_mapper_id) REFERENCES public.protocol_mapper(id);


--
-- Name: default_client_scope fk_r_def_cli_scope_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT fk_r_def_cli_scope_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: required_action_provider fk_req_act_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT fk_req_act_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_uris fk_resource_server_uris; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT fk_resource_server_uris FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: role_attribute fk_role_attribute_id; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT fk_role_attribute_id FOREIGN KEY (role_id) REFERENCES public.keycloak_role(id);


--
-- Name: frames fk_story; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.frames
    ADD CONSTRAINT fk_story FOREIGN KEY (story_id) REFERENCES public.stories(id);


--
-- Name: history fk_story; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.history
    ADD CONSTRAINT fk_story FOREIGN KEY (component_id) REFERENCES public.stories(id);


--
-- Name: realm_supported_locales fk_supported_locales_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT fk_supported_locales_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_config fk_t13hpu1j94r2ebpekr39x5eu5; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT fk_t13hpu1j94r2ebpekr39x5eu5 FOREIGN KEY (user_federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_group_membership fk_user_group_user; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT fk_user_group_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: policy_config fkdc34197cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT fkdc34197cf864c4e43 FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: identity_provider_config fkdc4897cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT fkdc4897cf864c4e43 FOREIGN KEY (identity_provider_id) REFERENCES public.identity_provider(internal_id);


--
-- PostgreSQL database dump complete
--

