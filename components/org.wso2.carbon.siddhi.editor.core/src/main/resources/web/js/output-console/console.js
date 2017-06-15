/**
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
define(['log', 'backbone'], function (log, Backbone) {

    var Console = Backbone.View.extend(
        /** @lends Console.prototype */
        {
            /**
             * @augments Backbone.View
             * @constructs
             * @class ConsoleView represents the view for console.
             */
            initialize: function (options) {

                _.set(this, 'id', this.cid);
                _.set(this, '_title', _.get(options, 'title'));
                if (!_.has(options, 'template')){
                    errMsg = 'unable to find config template ' + _.toString(options);
                    log.error(errMsg);
                    throw errMsg;
                }
                template = $(_.get(options, 'template'));
                if(!template.length > 0){
                    errMsg = 'unable to find template with id ' + _.get(options, 'template');
                    log.error(errMsg);
                    throw errMsg;
                }
                this._template = template;
                this.options = options;
                this._isActive = false;

                if (_.has(options, 'parent')){
                  this.setParent(_.get(options, 'parent'));
                }

                // create the console template
                var console = this._template.children('div').clone();
                this.getParent().getConsoleContainer().append(console);

                var consoleClass = _.get(this.options, 'cssClass.console');
                console.addClass(consoleClass);
                console.attr('id', this.cid);
                this.$el = console;
            },
            setActive: function(isActive){
//                if(_.isBoolean(isActive)){
//                    this._isActive = isActive;
//                    if (isActive){
//                        this.$el.addClass(_.get(this.options, 'cssClass.tab_active'));
//                    } else {
//                        this.$el.removeClass(_.get(this.options, 'cssClass.tab_active'));
//                    }
//                }
            },
            isActive: function(){
                return this._isActive;
            },
            setHeader: function(header){
                this._consoleHeader = header;
            },
            getHeader: function(){
                return this._consoleHeader;
            },
            getContentContainer: function(){
                return this.$el.get(0);
            },
            getParent: function(){
                return this._parentTabList;
            },
            setParent: function(parentTabList){
                this._parentTabList = parentTabList;
            },
            getTitle: function(){
                return _.isNil(this._title) ? "untitled" : this._title;
            },
            setTitle: function(title){
                this._title = title;
                this.trigger('title-changed', title);
            }
        });

    return Console;
});