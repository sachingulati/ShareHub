
<span class="text-right" style="float: right; padding: 0px; margin: 0px">
    <div class="dropdown">
        <button class="btn btn-link dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" style="margin-left: 0px; padding-left: 0px">
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
            <li role="presentation">
                <a href="javascript:void(0)" class="resourceEditButton" role="menuitem" tabindex="-1" onclick='openEditResource("${resource.id}",this)'>Edit</a>
            </li>
            <li role="presentation">
                <g:remoteLink onSuccess="afterResourceDelete(data)" role="menuitem" tabindex="-1" controller="resource" action="deleteResource" params="[id: resource.id]">Delete </g:remoteLink>
            </li>
        </ul>
    </div>
</span>