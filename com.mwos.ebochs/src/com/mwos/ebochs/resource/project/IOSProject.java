package com.mwos.ebochs.resource.project;

import java.net.URI;
import java.util.Map;

import org.eclipse.core.resources.FileInfoMatcherDescription;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceFilterDescription;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentTypeMatcher;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

public abstract class IOSProject implements IProject {
	private IProject p;

	public IOSProject(IProject p) {
		// TODO Auto-generated constructor stub
		this.p = p;
	}

	@Override
	public boolean exists(IPath path) {
		// TODO Auto-generated method stub
		return p.exists(path);
	}

	@Override
	public IResource findMember(String path) {
		// TODO Auto-generated method stub
		return p.findMember(path);
	}

	@Override
	public IResource findMember(String path, boolean includePhantoms) {
		// TODO Auto-generated method stub
		return p.findMember(path, includePhantoms);
	}

	@Override
	public IResource findMember(IPath path) {
		// TODO Auto-generated method stub
		return p.findMember(path);
	}

	@Override
	public IResource findMember(IPath path, boolean includePhantoms) {
		// TODO Auto-generated method stub
		return p.findMember(path, includePhantoms);
	}

	@Override
	public String getDefaultCharset() throws CoreException {
		// TODO Auto-generated method stub
		return p.getDefaultCharset();
	}

	@Override
	public String getDefaultCharset(boolean checkImplicit) throws CoreException {
		// TODO Auto-generated method stub
		return p.getDefaultCharset(checkImplicit);
	}

	@Override
	public IFile getFile(IPath path) {
		// TODO Auto-generated method stub
		return p.getFile(path);
	}

	@Override
	public IFolder getFolder(IPath path) {
		// TODO Auto-generated method stub
		return p.getFolder(path);
	}

	@Override
	public IResource[] members() throws CoreException {
		// TODO Auto-generated method stub
		return p.members();
	}

	@Override
	public IResource[] members(boolean includePhantoms) throws CoreException {
		// TODO Auto-generated method stub
		return p.members(includePhantoms);
	}

	@Override
	public IResource[] members(int memberFlags) throws CoreException {
		// TODO Auto-generated method stub
		return p.members(memberFlags);
	}

	@Override
	public IFile[] findDeletedMembersWithHistory(int depth, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return p.findDeletedMembersWithHistory(depth, monitor);
	}

	@Override
	public void setDefaultCharset(String charset) throws CoreException {
		// TODO Auto-generated method stub
		p.setDefaultCharset(charset);
	}

	@Override
	public void setDefaultCharset(String charset, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.setDefaultCharset(charset, monitor);
	}

	@Override
	public IResourceFilterDescription createFilter(int type, FileInfoMatcherDescription matcherDescription, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return p.createFilter(type, matcherDescription, updateFlags, monitor);
	}

	@Override
	public IResourceFilterDescription[] getFilters() throws CoreException {
		// TODO Auto-generated method stub
		return p.getFilters();
	}

	@Override
	public void accept(IResourceProxyVisitor visitor, int memberFlags) throws CoreException {
		// TODO Auto-generated method stub
		p.accept(visitor, memberFlags);
	}

	@Override
	public void accept(IResourceProxyVisitor visitor, int depth, int memberFlags) throws CoreException {
		// TODO Auto-generated method stub
		p.accept(visitor, depth, memberFlags);
	}

	@Override
	public void accept(IResourceVisitor visitor) throws CoreException {
		// TODO Auto-generated method stub
		p.accept(visitor);
	}

	@Override
	public void accept(IResourceVisitor visitor, int depth, boolean includePhantoms) throws CoreException {
		// TODO Auto-generated method stub
		p.accept(visitor, depth, includePhantoms);
	}

	@Override
	public void accept(IResourceVisitor visitor, int depth, int memberFlags) throws CoreException {
		// TODO Auto-generated method stub
		p.accept(visitor, depth, memberFlags);
	}

	@Override
	public void clearHistory(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.clearHistory(monitor);
	}

	@Override
	public void copy(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.copy(destination, force, monitor);
	}

	@Override
	public void copy(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.copy(destination, updateFlags, monitor);
	}

	@Override
	public void copy(IProjectDescription description, boolean force, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.copy(description, force, monitor);
	}

	@Override
	public void copy(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.copy(description, updateFlags, monitor);
	}

	@Override
	public IMarker createMarker(String type) throws CoreException {
		// TODO Auto-generated method stub
		return p.createMarker(type);
	}

	@Override
	public IResourceProxy createProxy() {
		// TODO Auto-generated method stub
		return p.createProxy();
	}

	@Override
	public void delete(boolean force, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.delete(force, monitor);
	}

	@Override
	public void delete(int updateFlags, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.delete(updateFlags, monitor);
	}

	@Override
	public void deleteMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
		// TODO Auto-generated method stub
		p.deleteMarkers(type, includeSubtypes, depth);
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return p.exists();
	}

	@Override
	public IMarker findMarker(long id) throws CoreException {
		// TODO Auto-generated method stub
		return p.findMarker(id);
	}

	@Override
	public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth) throws CoreException {
		// TODO Auto-generated method stub
		return p.findMarkers(type, includeSubtypes, depth);
	}

	@Override
	public int findMaxProblemSeverity(String type, boolean includeSubtypes, int depth) throws CoreException {
		// TODO Auto-generated method stub
		return p.findMaxProblemSeverity(type, includeSubtypes, depth);
	}

	@Override
	public String getFileExtension() {
		// TODO Auto-generated method stub
		return p.getFileExtension();
	}

	@Override
	public IPath getFullPath() {
		// TODO Auto-generated method stub
		return p.getFullPath();
	}

	@Override
	public long getLocalTimeStamp() {
		// TODO Auto-generated method stub
		return p.getLocalTimeStamp();
	}

	@Override
	public IPath getLocation() {
		// TODO Auto-generated method stub
		return p.getLocation();
	}

	@Override
	public URI getLocationURI() {
		// TODO Auto-generated method stub
		return p.getLocationURI();
	}

	@Override
	public IMarker getMarker(long id) {
		// TODO Auto-generated method stub
		return p.getMarker(id);
	}

	@Override
	public long getModificationStamp() {
		// TODO Auto-generated method stub
		return p.getModificationStamp();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return p.getName();
	}

	@Override
	public IPathVariableManager getPathVariableManager() {
		// TODO Auto-generated method stub
		return p.getPathVariableManager();
	}

	@Override
	public IContainer getParent() {
		// TODO Auto-generated method stub
		return p.getParent();
	}

	@Override
	public Map<QualifiedName, String> getPersistentProperties() throws CoreException {
		// TODO Auto-generated method stub
		return p.getPersistentProperties();
	}

	@Override
	public String getPersistentProperty(QualifiedName key) throws CoreException {
		// TODO Auto-generated method stub
		return p.getPersistentProperty(key);
	}

	@Override
	public IProject getProject() {
		// TODO Auto-generated method stub
		return p.getProject();
	}

	@Override
	public IPath getProjectRelativePath() {
		// TODO Auto-generated method stub
		return p.getProjectRelativePath();
	}

	@Override
	public IPath getRawLocation() {
		// TODO Auto-generated method stub
		return p.getRawLocation();
	}

	@Override
	public URI getRawLocationURI() {
		// TODO Auto-generated method stub
		return p.getRawLocationURI();
	}

	@Override
	public ResourceAttributes getResourceAttributes() {
		// TODO Auto-generated method stub
		return p.getResourceAttributes();
	}

	@Override
	public Map<QualifiedName, Object> getSessionProperties() throws CoreException {
		// TODO Auto-generated method stub
		return p.getSessionProperties();
	}

	@Override
	public Object getSessionProperty(QualifiedName key) throws CoreException {
		// TODO Auto-generated method stub
		return p.getSessionProperty(key);
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return p.getType();
	}

	@Override
	public IWorkspace getWorkspace() {
		// TODO Auto-generated method stub
		return p.getWorkspace();
	}

	@Override
	public boolean isAccessible() {
		// TODO Auto-generated method stub
		return p.isAccessible();
	}

	@Override
	public boolean isDerived() {
		// TODO Auto-generated method stub
		return p.isDerived();
	}

	@Override
	public boolean isDerived(int options) {
		// TODO Auto-generated method stub
		return p.isDerived(options);
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return p.isHidden();
	}

	@Override
	public boolean isHidden(int options) {
		// TODO Auto-generated method stub
		return p.isHidden(options);
	}

	@Override
	public boolean isLinked() {
		// TODO Auto-generated method stub
		return p.isLinked();
	}

	@Override
	public boolean isVirtual() {
		// TODO Auto-generated method stub
		return p.isVirtual();
	}

	@Override
	public boolean isLinked(int options) {
		// TODO Auto-generated method stub
		return p.isLinked(options);
	}

	@Override
	public boolean isLocal(int depth) {
		// TODO Auto-generated method stub
		return p.isLocal(depth);
	}

	@Override
	public boolean isPhantom() {
		// TODO Auto-generated method stub
		return p.isPhantom();
	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return p.isReadOnly();
	}

	@Override
	public boolean isSynchronized(int depth) {
		// TODO Auto-generated method stub
		return p.isSynchronized(depth);
	}

	@Override
	public boolean isTeamPrivateMember() {
		// TODO Auto-generated method stub
		return p.isTeamPrivateMember();
	}

	@Override
	public boolean isTeamPrivateMember(int options) {
		// TODO Auto-generated method stub
		return p.isTeamPrivateMember(options);
	}

	@Override
	public void move(IPath destination, boolean force, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.move(destination, force, monitor);
	}

	@Override
	public void move(IPath destination, int updateFlags, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.move(destination, updateFlags, monitor);
	}

	@Override
	public void move(IProjectDescription description, boolean force, boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.move(description, force, keepHistory, monitor);
	}

	@Override
	public void move(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.move(description, updateFlags, monitor);
	}

	@Override
	public void refreshLocal(int depth, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.refreshLocal(depth, monitor);
	}

	@Override
	public void revertModificationStamp(long value) throws CoreException {
		// TODO Auto-generated method stub
		p.revertModificationStamp(value);
	}

	@Override
	public void setDerived(boolean isDerived) throws CoreException {
		// TODO Auto-generated method stub
		p.setDerived(isDerived);
	}

	@Override
	public void setDerived(boolean isDerived, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.setDerived(isDerived, monitor);
	}

	@Override
	public void setHidden(boolean isHidden) throws CoreException {
		// TODO Auto-generated method stub
		p.setHidden(isHidden);
	}

	@Override
	public void setLocal(boolean flag, int depth, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.setLocal(flag, depth, monitor);
	}

	@Override
	public long setLocalTimeStamp(long value) throws CoreException {
		// TODO Auto-generated method stub
		return p.setLocalTimeStamp(value);
	}

	@Override
	public void setPersistentProperty(QualifiedName key, String value) throws CoreException {
		// TODO Auto-generated method stub
		p.setPersistentProperty(key, value);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		// TODO Auto-generated method stub
		p.setReadOnly(readOnly);
	}

	@Override
	public void setResourceAttributes(ResourceAttributes attributes) throws CoreException {
		// TODO Auto-generated method stub
		p.setResourceAttributes(attributes);
	}

	@Override
	public void setSessionProperty(QualifiedName key, Object value) throws CoreException {
		// TODO Auto-generated method stub
		p.setSessionProperty(key, value);
	}

	@Override
	public void setTeamPrivateMember(boolean isTeamPrivate) throws CoreException {
		// TODO Auto-generated method stub
		p.setTeamPrivateMember(isTeamPrivate);
	}

	@Override
	public void touch(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.touch(monitor);
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return p.getAdapter(adapter);
	}

	@Override
	public boolean contains(ISchedulingRule rule) {
		// TODO Auto-generated method stub
		return p.contains(rule);
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		// TODO Auto-generated method stub
		return p.isConflicting(rule);
	}

	@Override
	public void build(int kind, String builderName, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.build(kind, builderName, args, monitor);
	}

	@Override
	public void build(int kind, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.build(kind, monitor);
	}

	@Override
	public void build(IBuildConfiguration config, int kind, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.build(config, kind, monitor);
	}

	@Override
	public void close(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.close(monitor);
	}

	@Override
	public void create(IProjectDescription description, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.create(description, monitor);
	}

	@Override
	public void create(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.create(monitor);
	}

	@Override
	public void create(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.create(description, updateFlags, monitor);
	}

	@Override
	public void delete(boolean deleteContent, boolean force, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.delete(deleteContent, force, monitor);
	}

	@Override
	public IBuildConfiguration getActiveBuildConfig() throws CoreException {
		// TODO Auto-generated method stub
		return p.getActiveBuildConfig();
	}

	@Override
	public IBuildConfiguration getBuildConfig(String configName) throws CoreException {
		// TODO Auto-generated method stub
		return p.getBuildConfig(configName);
	}

	@Override
	public IBuildConfiguration[] getBuildConfigs() throws CoreException {
		// TODO Auto-generated method stub
		return p.getBuildConfigs();
	}

	@Override
	public IContentTypeMatcher getContentTypeMatcher() throws CoreException {
		// TODO Auto-generated method stub
		return p.getContentTypeMatcher();
	}

	@Override
	public IProjectDescription getDescription() throws CoreException {
		// TODO Auto-generated method stub
		return p.getDescription();
	}

	@Override
	public IFile getFile(String name) {
		// TODO Auto-generated method stub
		return p.getFile(name);
	}

	@Override
	public IFolder getFolder(String name) {
		// TODO Auto-generated method stub
		return p.getFolder(name);
	}

	@Override
	public IProjectNature getNature(String natureId) throws CoreException {
		// TODO Auto-generated method stub
		return p.getNature(natureId);
	}

	@Override
	public IPath getPluginWorkingLocation(IPluginDescriptor plugin) {
		// TODO Auto-generated method stub
		return p.getPluginWorkingLocation(plugin);
	}

	@Override
	public IPath getWorkingLocation(String id) {
		// TODO Auto-generated method stub
		return p.getWorkingLocation(id);
	}

	@Override
	public IProject[] getReferencedProjects() throws CoreException {
		// TODO Auto-generated method stub
		return p.getReferencedProjects();
	}

	@Override
	public void clearCachedDynamicReferences() {
		// TODO Auto-generated method stub
		p.clearCachedDynamicReferences();
	}

	@Override
	public IProject[] getReferencingProjects() {
		// TODO Auto-generated method stub
		return p.getReferencingProjects();
	}

	@Override
	public IBuildConfiguration[] getReferencedBuildConfigs(String configName, boolean includeMissing) throws CoreException {
		// TODO Auto-generated method stub
		return p.getReferencedBuildConfigs(configName, includeMissing);
	}

	@Override
	public boolean hasBuildConfig(String configName) throws CoreException {
		// TODO Auto-generated method stub
		return p.hasBuildConfig(configName);
	}

	@Override
	public boolean hasNature(String natureId) throws CoreException {
		// TODO Auto-generated method stub
		return p.hasNature(natureId);
	}

	@Override
	public boolean isNatureEnabled(String natureId) throws CoreException {
		// TODO Auto-generated method stub
		return p.isNatureEnabled(natureId);
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return p.isOpen();
	}

	@Override
	public void loadSnapshot(int options, URI snapshotLocation, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.loadSnapshot(options, snapshotLocation, monitor);
	}

	@Override
	public void move(IProjectDescription description, boolean force, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.move(description, force, monitor);
	}

	@Override
	public void open(int updateFlags, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.open(updateFlags, monitor);
	}

	@Override
	public void open(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.open(monitor);
	}

	@Override
	public void saveSnapshot(int options, URI snapshotLocation, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.saveSnapshot(options, snapshotLocation, monitor);
	}

	@Override
	public void setDescription(IProjectDescription description, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.setDescription(description, monitor);
	}

	@Override
	public void setDescription(IProjectDescription description, int updateFlags, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		p.setDescription(description, updateFlags, monitor);
	}

}
